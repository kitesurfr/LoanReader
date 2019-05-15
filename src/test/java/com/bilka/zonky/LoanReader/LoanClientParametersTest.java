package com.bilka.zonky.LoanReader;

import static com.bilka.zonky.LoanReader.client.LoanClientParameters.DATE_PUBLISHED_FIELD;
import static com.bilka.zonky.LoanReader.client.LoanClientParameters.GREATER_THEN_OPERATION_SUFFIX;

import org.junit.Assert;
import org.junit.Test;

import com.bilka.zonky.LoanReader.client.LoanClientParameters;
import com.bilka.zonky.LoanReader.client.LoanClientParameters.ORDER_DIRECTION;


public class LoanClientParametersTest {

	
	@Test
	public void filterByQueryTest() {
		
		String uriVariable = LoanClientParameters.filterByQuery(DATE_PUBLISHED_FIELD, GREATER_THEN_OPERATION_SUFFIX); 
		String expectedString = DATE_PUBLISHED_FIELD + "__" + GREATER_THEN_OPERATION_SUFFIX;
		
		Assert.assertEquals(expectedString, uriVariable);
	}
	
	
	@Test
	public void orderByQueryTest() {
		
		String string = LoanClientParameters.orderByQuery(ORDER_DIRECTION.ASC, DATE_PUBLISHED_FIELD);
		String expectedString = ORDER_DIRECTION.ASC + DATE_PUBLISHED_FIELD;
		
		Assert.assertEquals(expectedString, string);
	}
	
}