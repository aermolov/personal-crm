package io.github.aermolov.personalcrm.crm.api;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Contact(
		UUID id,
		String name,
		String email,
		String notes,
		OffsetDateTime createdAt) {
}
