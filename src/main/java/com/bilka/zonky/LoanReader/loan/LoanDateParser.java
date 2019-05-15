package com.bilka.zonky.LoanReader.loan;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class LoanDateParser {

	public static final DateTimeFormatter LOAN_DATE_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
	
	private static final Logger log = LoggerFactory.getLogger(LoanDateParser.class);
	
	public static ZonedDateTime parse(String text) {
		
		if (StringUtils.isEmpty(text)) {
			return null;
		}
		
		ZonedDateTime dateTime;
		try {
			dateTime = ZonedDateTime.parse(text, LOAN_DATE_FORMATTER);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
		
		return dateTime;
	}
	
}
