package io.github.aermolov.personalcrm.crm.persistence;

import io.github.aermolov.personalcrm.crm.api.Interaction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "interactions")
class InteractionEntity {

	@Id
	@Column(name = "id", nullable = false)
	private UUID id;

	@Column(name = "owner_sub", nullable = false)
	private String ownerSub;

	@Column(name = "contact_id", nullable = false)
	private UUID contactId;

	@Column(name = "occurred_at", nullable = false)
	private OffsetDateTime occurredAt;

	@Column(name = "summary", nullable = false)
	private String summary;

	protected InteractionEntity() {
	}

	InteractionEntity(
			UUID id,
			String ownerSub,
			UUID contactId,
			OffsetDateTime occurredAt,
			String summary) {
		this.id = id;
		this.ownerSub = ownerSub;
		this.contactId = contactId;
		this.occurredAt = occurredAt;
		this.summary = summary;
	}

	Interaction toRecord() {
		return new Interaction(id, contactId, occurredAt, summary);
	}
}
