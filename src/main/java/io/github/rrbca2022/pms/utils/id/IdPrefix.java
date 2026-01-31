package io.github.rrbca2022.pms.utils.id;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to define prefix for alphanumeric ID generator.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface IdPrefix {
	String value() default "";
}

