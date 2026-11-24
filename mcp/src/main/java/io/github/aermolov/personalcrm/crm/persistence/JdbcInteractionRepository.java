package io.github.aermolov.personalcrm.crm.persistence;

import io.github.aermolov.personalcrm.crm.api.Interaction;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcInteractionRepository implements InteractionRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcInteractionRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Interaction insert(String sub, UUID contactId, OffsetDateTime occurredAt, String summary) {
		UUID id = UUID.randomUUID();
		jdbcTemplate.update(
				"INSERT INTO interactions (id, owner_sub, contact_id, occurred_at, summary) "
						+ "VALUES (?, ?, ?, ?, ?)",
				id, sub, contactId, occurredAt, summary);
		return new Interaction(id, contactId, occurredAt, summary);
	}

	@Override
	public List<Interaction> findByOwnerAndContact(String sub, UUID contactId) {
		return jdbcTemplate.query(
				"SELECT id, contact_id, occurred_at, summary FROM interactions "
						+ "WHERE owner_sub = ? AND contact_id = ? ORDER BY occurred_at, id",
				this::mapInteraction, sub, contactId);
	}

	private Interaction mapInteraction(ResultSet resultSet, int rowNumber) throws SQLException {
		return new Interaction(
				resultSet.getObject("id", UUID.class),
				resultSet.getObject("contact_id", UUID.class),
				resultSet.getObject("occurred_at", OffsetDateTime.class),
				resultSet.getString("summary"));
	}
}
