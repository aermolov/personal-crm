package io.github.aermolov.personalcrm.crm.persistence;

import jakarta.persistence.criteria.Expression;
import java.util.Locale;
import org.springframework.data.jpa.domain.Specification;

final class ContactSpecifications {

	private static final char LIKE_ESCAPE = '\\';

	private ContactSpecifications() {
	}

	static Specification<ContactEntity> ownedByAndMatching(String ownerSub, String text) {
		String pattern = "%" + escapeLike(text.toLowerCase(Locale.ROOT)) + "%";
		return (root, query, criteriaBuilder) -> {
			Expression<String> lowerName = criteriaBuilder.lower(root.get("name"));
			Expression<String> lowerEmail = criteriaBuilder.lower(
					criteriaBuilder.coalesce(root.get("email"), ""));
			return criteriaBuilder.and(
					criteriaBuilder.equal(root.get("ownerSub"), ownerSub),
					criteriaBuilder.or(
							criteriaBuilder.like(lowerName, pattern, LIKE_ESCAPE),
							criteriaBuilder.like(lowerEmail, pattern, LIKE_ESCAPE)));
		};
	}

	private static String escapeLike(String value) {
		return value.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
	}
}
