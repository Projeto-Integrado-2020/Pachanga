package com.eventmanager.pachanga.utils;


import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Logger {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);
	
	public static void logWarning(String warning) {
		logger.warn(warning);
	}
	
	public static void logDebug(String warning) {
		logger.debug(warning);
	}
	
	public static void logError(String warning) {
		logger.error(warning);
	}
	
	public static void logInfo(String warning) {
		logger.info(warning);
	}
}
