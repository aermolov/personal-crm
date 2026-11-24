package io.github.aermolov.personalcrm.crm.persistence;

import io.github.aermolov.personalcrm.crm.api.Interaction;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
class InteractionRepositoryAdapter implements InteractionRepository {

	private final InteractionJpaRepository repository;

	InteractionRepositoryAdapter(InteractionJpaRepository repository) {
		this.repository = repository;
	}

	@Override
	public Interaction insert(String sub, UUID contactId, OffsetDateTime occurredAt, String summary) {
		InteractionEntity entity = new InteractionEntity(
				UUID.randomUUID(),
				sub,
				contactId,
				occurredAt,
				summary);
		return repository.save(entity).toRecord();
	}

	@Override
	public List<Interaction> findByOwnerAndContact(String sub, UUID contactId) {
		return repository.findAllByOwnerSubAndContactIdOrderByOccurredAtAscIdAsc(sub, contactId)
				.stream()
				.map(InteractionEntity::toRecord)
				.toList();
	}
}
