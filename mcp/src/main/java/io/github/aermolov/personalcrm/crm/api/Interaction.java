package io.github.aermolov.personalcrm.crm.api;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Interaction(
		UUID id,
		UUID contactId,
		OffsetDateTime occurredAt,
		String summary) {
}
