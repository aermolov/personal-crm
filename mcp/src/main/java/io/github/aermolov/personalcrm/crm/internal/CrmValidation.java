package io.github.aermolov.personalcrm.crm.internal;

import io.github.aermolov.personalcrm.crm.api.InvalidInputException;
import java.util.regex.Pattern;

final class CrmValidation {

	private static final Pattern EMAIL_PATTERN =
			Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

	private CrmValidation() {
	}

	static String requiredSub(String sub) {
		return requiredText(sub, "sub");
	}

	static String requiredText(String value, String field) {
		if (value == null || value.trim().isEmpty()) {
			throw new InvalidInputException(field + " must not be blank");
		}
		return value.trim();
	}

	static String optionalText(String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		return value.trim();
	}

	static String email(String value) {
		String email = optionalText(value);
		if (email != null && !EMAIL_PATTERN.matcher(email).matches()) {
			throw new InvalidInputException("email must be a valid email address");
		}
		return email;
	}
}
