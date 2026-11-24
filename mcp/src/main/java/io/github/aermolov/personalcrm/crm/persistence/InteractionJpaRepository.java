package io.github.aermolov.personalcrm.crm.persistence;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteractionJpaRepository extends JpaRepository<InteractionEntity, UUID> {

	List<InteractionEntity> findAllByOwnerSubAndContactIdOrderByOccurredAtAscIdAsc(
			String ownerSub,
			UUID contactId);
}
