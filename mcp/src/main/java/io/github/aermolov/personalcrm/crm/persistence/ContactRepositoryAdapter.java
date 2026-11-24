package io.github.aermolov.personalcrm.crm.persistence;

import io.github.aermolov.personalcrm.crm.api.Contact;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
class ContactRepositoryAdapter implements ContactRepository {

	private final ContactJpaRepository repository;

	ContactRepositoryAdapter(ContactJpaRepository repository) {
		this.repository = repository;
	}

	@Override
	public Contact insert(String sub, String name, String email, String notes) {
		ContactEntity entity = new ContactEntity(
				UUID.randomUUID(),
				sub,
				name,
				email,
				notes,
				OffsetDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MICROS));
		return repository.save(entity).toRecord();
	}

	@Override
	public List<Contact> findByOwner(String sub) {
		return repository.findAllByOwnerSubOrderByCreatedAtAscIdAsc(sub).stream()
				.map(ContactEntity::toRecord)
				.toList();
	}

	@Override
	public List<Contact> searchByOwner(String sub, String text) {
		Sort sort = Sort.by(Sort.Order.asc("createdAt"), Sort.Order.asc("id"));
		return repository.findAll(ContactSpecifications.ownedByAndMatching(sub, text), sort)
				.stream()
				.map(ContactEntity::toRecord)
				.toList();
	}

	@Override
	public boolean existsByOwnerAndId(String sub, UUID contactId) {
		return repository.existsByIdAndOwnerSub(contactId, sub);
	}
}
