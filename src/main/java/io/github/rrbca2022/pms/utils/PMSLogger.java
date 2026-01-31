package io.github.rrbca2022.pms.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.rrbca2022.pms.entity.LogLevel;

public class PMSLogger {

	private static final Logger log = LoggerFactory.getLogger("PMSLogger");

	private static LogLevel currentLevel = LogLevel.INFO;

	public static void setLogLevel(LogLevel level) {
		currentLevel = level;
		log.info("Log level set to {}", level);
	}

	public static void info(String m) {
		if (currentLevel == LogLevel.INFO || currentLevel == LogLevel.DEBUG) {
			log.info(m);
		}
	}

	public static void warn(String m) {
		if (currentLevel == LogLevel.INFO || currentLevel == LogLevel.DEBUG) {
			log.warn(m);
		}
	}

	public static void error(String m) {
		log.error(m);
	}

	public static void debug(String m) {
		if (currentLevel == LogLevel.DEBUG) {
			log.debug(m);
		}
	}
}
