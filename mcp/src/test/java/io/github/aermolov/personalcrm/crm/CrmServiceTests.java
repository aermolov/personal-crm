package io.github.aermolov.personalcrm.crm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.aermolov.personalcrm.crm.api.Contact;
import io.github.aermolov.personalcrm.crm.api.ContactNotFoundException;
import io.github.aermolov.personalcrm.crm.api.ContactService;
import io.github.aermolov.personalcrm.crm.api.InteractionService;
import io.github.aermolov.personalcrm.crm.api.InvalidInputException;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class CrmServiceTests {

	private static final String SUB_A = "crm-test-a";
	private static final String SUB_B = "crm-test-b";
	private static final OffsetDateTime OCCURRED_AT = OffsetDateTime.parse("2026-01-02T03:04:05Z");

	@Autowired
	private ContactService contactService;

	@Autowired
	private InteractionService interactionService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void clearData() {
		jdbcTemplate.update("DELETE FROM interactions");
		jdbcTemplate.update("DELETE FROM contacts");
	}

	@Test
	void createsListsAndSearchesContacts() {
		Contact contact = contactService.create(SUB_A, " Ada Lovelace ", "ada@example.com", " Notes ");

		assertEquals("Ada Lovelace", contact.name());
		assertEquals("ada@example.com", contact.email());
		assertEquals("Notes", contact.notes());
		assertEquals(List.of(contact), contactService.list(SUB_A));
		assertEquals(List.of(contact), contactService.search(SUB_A, "ADA"));
	}

	@Test
	void wildcardCharactersInSearchAreLiteral() {
		Contact percentContact = contactService.create(SUB_A, "100% Match", null, null);
		contactService.create(SUB_A, "1000 Match", null, null);

		assertEquals(List.of(percentContact), contactService.search(SUB_A, "%"));
	}

	@Test
	void validationErrorsDoNotWrite() {
		assertEquals(0, contactService.list(SUB_A).size());

		assertThrows(InvalidInputException.class,
				() -> contactService.create(SUB_A, " ", "valid@example.com", null));
		assertThrows(InvalidInputException.class,
				() -> contactService.create(SUB_A, "Name", "not-an-email", null));

		assertEquals(0, contactService.list(SUB_A).size());
	}

	@Test
	void ownerIsolationAppliesToContactsAndInteractions() {
		Contact contact = contactService.create(SUB_A, "Private Contact", null, null);

		assertEquals(List.of(contact), contactService.list(SUB_A));
		assertEquals(List.of(), contactService.list(SUB_B));
		assertEquals(List.of(), contactService.search(SUB_B, "Private"));

		assertThrows(ContactNotFoundException.class,
				() -> interactionService.log(SUB_B, contact.id(), OCCURRED_AT, "Attempted access"));
		assertThrows(ContactNotFoundException.class,
				() -> interactionService.list(SUB_B, contact.id()));

		interactionService.log(SUB_A, contact.id(), OCCURRED_AT, "Called");
		assertEquals(1, interactionService.list(SUB_A, contact.id()).size());
	}
}
