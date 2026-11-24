package io.github.aermolov.personalcrm.crm.api;

import java.util.List;

public interface ContactService {

	Contact create(String sub, String name, String email, String notes);

	List<Contact> list(String sub);

	List<Contact> search(String sub, String text);
}
