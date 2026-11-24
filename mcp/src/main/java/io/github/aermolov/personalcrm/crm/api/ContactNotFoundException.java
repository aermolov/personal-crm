package io.github.aermolov.personalcrm.crm.api;

import java.util.UUID;

public class ContactNotFoundException extends CrmException {

	public ContactNotFoundException(UUID contactId) {
		super("Contact not found: " + contactId);
	}
}
