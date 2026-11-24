package io.github.aermolov.personalcrm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class PersonalCrmApplicationTests {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void connectsToPostgres() {
		assertEquals(1, jdbcTemplate.queryForObject("SELECT 1", Integer.class));
	}

	@Test
	void flywayCreatesOwnerScopedSchemaAndConstraints() {
		assertEquals(1, jdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM flyway_schema_history WHERE version = '1' AND success",
				Integer.class));
		assertEquals(2, jdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM information_schema.tables "
						+ "WHERE table_schema = current_schema() "
						+ "AND table_name IN ('contacts', 'interactions')",
				Integer.class));
		assertEquals(3, jdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM pg_indexes WHERE schemaname = current_schema() "
						+ "AND indexname IN ('contacts_owner_sub_idx', "
						+ "'interactions_owner_sub_idx', 'interactions_owner_sub_contact_id_idx')",
				Integer.class));

		assertThrows(DataAccessException.class, () -> jdbcTemplate.update(
				"INSERT INTO contacts (id, owner_sub, name) VALUES (?, ?, ?)",
				UUID.randomUUID(), "schema-test", "  "));
		assertThrows(DataAccessException.class, () -> jdbcTemplate.update(
				"INSERT INTO interactions (id, owner_sub, contact_id, occurred_at, summary) "
						+ "VALUES (?, ?, ?, now(), ?)",
				UUID.randomUUID(), "schema-test", UUID.randomUUID(), "  "));
		assertThrows(DataAccessException.class, () -> jdbcTemplate.update(
				"INSERT INTO interactions (id, owner_sub, contact_id, occurred_at, summary) "
						+ "VALUES (?, ?, ?, now(), ?)",
				UUID.randomUUID(), "schema-test", UUID.randomUUID(), "Valid summary"));
	}

}
