package io.github.aermolov.personalcrm.crm.persistence;

import io.github.aermolov.personalcrm.crm.api.Contact;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "contacts")
class ContactEntity {

	@Id
	@Column(name = "id", nullable = false)
	private UUID id;

	@Column(name = "owner_sub", nullable = false)
	private String ownerSub;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "notes")
	private String notes;

	@Column(name = "created_at", nullable = false)
	private OffsetDateTime createdAt;

	protected ContactEntity() {
	}

	ContactEntity(
			UUID id,
			String ownerSub,
			String name,
			String email,
			String notes,
			OffsetDateTime createdAt) {
		this.id = id;
		this.ownerSub = ownerSub;
		this.name = name;
		this.email = email;
		this.notes = notes;
		this.createdAt = createdAt;
	}

	Contact toRecord() {
		return new Contact(id, name, email, notes, createdAt);
	}
}
