package io.github.aermolov.personalcrm.crm.persistence;

import io.github.aermolov.personalcrm.crm.api.Interaction;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface InteractionRepository {

	Interaction insert(String sub, UUID contactId, OffsetDateTime occurredAt, String summary);

	List<Interaction> findByOwnerAndContact(String sub, UUID contactId);
}
