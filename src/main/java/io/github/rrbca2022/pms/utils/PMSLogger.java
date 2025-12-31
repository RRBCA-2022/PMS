package io.github.rrbca2022.pms.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PMSLogger {
	private static final Logger log = LoggerFactory.getLogger("PMSLogger");

	public static void info(String m) {
		log.info(m);
	}

	public static void warn(String m) {
		log.warn(m);
	}

	public static void error(String m) {
		log.error(m);
	}

	public static void debug(String m) {
		log.debug(m);
	}

}
