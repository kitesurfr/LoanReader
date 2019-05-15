package com.bilka.zonky.LoanReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.ZonedDateTime;

import org.junit.Test;

import com.bilka.zonky.LoanReader.loan.LoanDateParser;

public class LoanDateParserTest {
	
	@Test
	public void parserOkTextTest (){
		
		String expected = "2016-04-19T18:25:41.208+02:00"; 
		
		ZonedDateTime ZonedDateTime = LoanDateParser.parse(expected);
		String actual = ZonedDateTime.format(LoanDateParser.LOAN_DATE_FORMATTER);
		
		assertNotNull(ZonedDateTime);
		assertEquals(expected, actual);
	}

	@Test
	public void parserNullTextTest (){
		String text = null; 
		ZonedDateTime ZonedDateTime = LoanDateParser.parse(text);
		assertNull(ZonedDateTime);
	}

	@Test
	public void parserEmptyTextTest (){
		String text = ""; 
		ZonedDateTime ZonedDateTime = LoanDateParser.parse(text);
		assertNull(ZonedDateTime);
	}

}
