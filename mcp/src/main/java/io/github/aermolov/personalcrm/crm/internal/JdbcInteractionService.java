package io.github.aermolov.personalcrm.crm.internal;

import io.github.aermolov.personalcrm.crm.api.ContactNotFoundException;
import io.github.aermolov.personalcrm.crm.api.Interaction;
import io.github.aermolov.personalcrm.crm.api.InteractionService;
import io.github.aermolov.personalcrm.crm.api.InvalidInputException;
import io.github.aermolov.personalcrm.crm.persistence.ContactRepository;
import io.github.aermolov.personalcrm.crm.persistence.InteractionRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class JdbcInteractionService implements InteractionService {

	private final ContactRepository contactRepository;
	private final InteractionRepository interactionRepository;

	public JdbcInteractionService(
			ContactRepository contactRepository,
			InteractionRepository interactionRepository) {
		this.contactRepository = contactRepository;
		this.interactionRepository = interactionRepository;
	}

	@Override
	public Interaction log(String sub, UUID contactId, OffsetDateTime occurredAt, String summary) {
		String ownerSub = CrmValidation.requiredSub(sub);
		if (contactId == null || !contactRepository.existsByOwnerAndId(ownerSub, contactId)) {
			throw new ContactNotFoundException(contactId);
		}
		if (occurredAt == null) {
			throw new InvalidInputException("occurredAt must not be null");
		}
		String interactionSummary = CrmValidation.requiredText(summary, "summary");
		return interactionRepository.insert(ownerSub, contactId, occurredAt, interactionSummary);
	}

	@Override
	public List<Interaction> list(String sub, UUID contactId) {
		String ownerSub = CrmValidation.requiredSub(sub);
		if (contactId == null || !contactRepository.existsByOwnerAndId(ownerSub, contactId)) {
			throw new ContactNotFoundException(contactId);
		}
		return interactionRepository.findByOwnerAndContact(ownerSub, contactId);
	}
}
