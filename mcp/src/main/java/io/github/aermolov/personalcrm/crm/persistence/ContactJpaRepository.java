package io.github.aermolov.personalcrm.crm.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContactJpaRepository
		extends JpaRepository<ContactEntity, UUID>, JpaSpecificationExecutor<ContactEntity> {

	List<ContactEntity> findAllByOwnerSubOrderByCreatedAtAscIdAsc(String ownerSub);

	Optional<ContactEntity> findByIdAndOwnerSub(UUID id, String ownerSub);

	boolean existsByIdAndOwnerSub(UUID id, String ownerSub);
}
