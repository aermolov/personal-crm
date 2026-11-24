package io.github.aermolov.personalcrm.crm.persistence;

import io.github.aermolov.personalcrm.crm.api.Contact;
import java.util.List;
import java.util.UUID;

public interface ContactRepository {

	Contact insert(String sub, String name, String email, String notes);

	List<Contact> findByOwner(String sub);

	List<Contact> searchByOwner(String sub, String text);

	boolean existsByOwnerAndId(String sub, UUID contactId);
}
