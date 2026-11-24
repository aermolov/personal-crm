package io.github.aermolov.personalcrm.crm.persistence;

import io.github.aermolov.personalcrm.crm.api.Contact;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcContactRepository implements ContactRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcContactRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Contact insert(String sub, String name, String email, String notes) {
		UUID id = UUID.randomUUID();
		jdbcTemplate.update(
				"INSERT INTO contacts (id, owner_sub, name, email, notes) VALUES (?, ?, ?, ?, ?)",
				id, sub, name, email, notes);
		return jdbcTemplate.queryForObject(
				"SELECT id, name, email, notes, created_at FROM contacts "
						+ "WHERE id = ? AND owner_sub = ?",
				this::mapContact, id, sub);
	}

	@Override
	public List<Contact> findByOwner(String sub) {
		return jdbcTemplate.query(
				"SELECT id, name, email, notes, created_at FROM contacts "
						+ "WHERE owner_sub = ? ORDER BY created_at, id",
				this::mapContact, sub);
	}

	@Override
	public List<Contact> searchByOwner(String sub, String text) {
		String pattern = "%" + escapeLike(text.toLowerCase(Locale.ROOT)) + "%";
		return jdbcTemplate.query(
				"SELECT id, name, email, notes, created_at FROM contacts "
						+ "WHERE owner_sub = ? "
						+ "AND (LOWER(name) LIKE ? ESCAPE '\\' "
						+ "OR LOWER(COALESCE(email, '')) LIKE ? ESCAPE '\\') "
						+ "ORDER BY created_at, id",
				this::mapContact, sub, pattern, pattern);
	}

	@Override
	public boolean existsByOwnerAndId(String sub, UUID contactId) {
		return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
				"SELECT EXISTS (SELECT 1 FROM contacts WHERE id = ? AND owner_sub = ?)",
				Boolean.class, contactId, sub));
	}

	private Contact mapContact(ResultSet resultSet, int rowNumber) throws SQLException {
		return new Contact(
				resultSet.getObject("id", UUID.class),
				resultSet.getString("name"),
				resultSet.getString("email"),
				resultSet.getString("notes"),
				resultSet.getObject("created_at", OffsetDateTime.class));
	}

	private static String escapeLike(String value) {
		return value.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
	}
}
