package com.eventmanager.pachanga.utils;


import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Logger {
	private static org.slf4j.Logger logBackEnd = LoggerFactory.getLogger(Logger.class);
	
	private Logger() {}
	
	public static void logWarning(String warning) {
		logBackEnd.warn(warning);
	}
	
	public static void logDebug(String warning) {
		logBackEnd.debug(warning);
	}
	
	public static void logError(String warning) {
		logBackEnd.error(warning);
	}
	
	public static void logInfo(String warning) {
		logBackEnd.info(warning);
	}
}
