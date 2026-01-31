package io.github.rrbca2022.pms.utils.id;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import java.io.Serializable;
import java.security.SecureRandom;

/**
 * Generates unique alphanumeric IDs with optional prefix defined by @IdPrefix.
 */
public class AlphaNumericIdGenerator implements IdentifierGenerator {

	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final int LENGTH = 10; // length of random part
	private static final SecureRandom RANDOM = new SecureRandom();

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) {
		String prefix = "";

		// Check if the entity class has @IdPrefix annotation
		if (object != null) {
			Class<?> clazz = object.getClass();
			IdPrefix annotation = clazz.getAnnotation(IdPrefix.class);
			if (annotation != null) {
				prefix = annotation.value();
			}
		}

		// Generate random alphanumeric string
		StringBuilder sb = new StringBuilder(LENGTH);
		for (int i = 0; i < LENGTH; i++) {
			sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
		}

		// Return final ID with prefix
		return prefix + sb.toString();
	}
}
