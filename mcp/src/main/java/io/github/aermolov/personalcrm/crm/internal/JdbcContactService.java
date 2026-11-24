package io.github.aermolov.personalcrm.crm.internal;

import io.github.aermolov.personalcrm.crm.api.Contact;
import io.github.aermolov.personalcrm.crm.api.ContactService;
import io.github.aermolov.personalcrm.crm.persistence.ContactRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JdbcContactService implements ContactService {

	private final ContactRepository contactRepository;

	public JdbcContactService(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	@Override
	public Contact create(String sub, String name, String email, String notes) {
		String ownerSub = CrmValidation.requiredSub(sub);
		String contactName = CrmValidation.requiredText(name, "name");
		return contactRepository.insert(
				ownerSub,
				contactName,
				CrmValidation.email(email),
				CrmValidation.optionalText(notes));
	}

	@Override
	public List<Contact> list(String sub) {
		return contactRepository.findByOwner(CrmValidation.requiredSub(sub));
	}

	@Override
	public List<Contact> search(String sub, String text) {
		return contactRepository.searchByOwner(
				CrmValidation.requiredSub(sub),
				CrmValidation.requiredText(text, "text"));
	}
}
