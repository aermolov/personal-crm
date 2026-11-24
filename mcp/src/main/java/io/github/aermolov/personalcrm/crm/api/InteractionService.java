package io.github.aermolov.personalcrm.crm.api;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface InteractionService {

	Interaction log(String sub, UUID contactId, OffsetDateTime occurredAt, String summary);

	List<Interaction> list(String sub, UUID contactId);
}
