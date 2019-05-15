package com.bilka.zonky.LoanReader.client;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import com.bilka.zonky.LoanReader.loan.LoanDateParser;

/**
 * Helper class to construct Zonky API call parameters
 */
public class LoanClientParameters {


	public enum ORDER_DIRECTION {
		
		ASC(""),
		DESC("-");
	
		private String direction;

		private ORDER_DIRECTION(String direction) {
			this.direction = direction;
		}

		@Override
		public String toString() {
			return direction;
		}
	}

	public static final String DATE_PUBLISHED_FIELD = "datePublished";

	public static final String HEADER_ORDER_FIELD = "X-Order";

	public static final String HEADER_PAGE_NUMBER_FIELD = "X-Page";
	
	public static final String HEADER_PAGE_SIZE_FIELD = "X-Size";

	public static final String GREATER_THEN_OPERATION_SUFFIX = "gt";

	private static final String FILTER_QUERY_FORMAT = "%s__%s"; 
	
	
	/**
	 * Filter query formatted as ${fieldName}__${operationSuffix}
	 * 
	 * @param fieldName
	 * @param operationSuffix
	 * @param filterValue
	 * @return query string
	 */
	public static String filterByQuery(String fieldName, String operationSuffix) {
		return String.format(FILTER_QUERY_FORMAT, fieldName, operationSuffix);
	}

	/**
	 * Order query used in client header section call. Format of the order query is $direction$fieldName.
	 * 
	 * @param direction ascending or descending
	 * @param fieldName
	 * @return order query
	 */
	public static String orderByQuery(ORDER_DIRECTION direction, String fieldName) {
		return direction + fieldName;
	}

	
	public static String getUrl(Map<String, String> params) {
		
		String url = LoanClient.ZONKY_MARKETPLACE_API_URL;
		
		if (params != null && params.size() != 0) {
			url = formatUrl(params);
		}
		
		return url;
	}
	
	public static Map<String, String> getParamMapForDateQuery() { 
		
		String filterByQuery = filterByQuery(DATE_PUBLISHED_FIELD, GREATER_THEN_OPERATION_SUFFIX); 
		
		Map<String,String> params = new HashMap<>();
		params.put(filterByQuery, "{" + DATE_PUBLISHED_FIELD + "}");
		
		return params;
	}

		
	public static Map<String, String> getParamMapForDate(ZonedDateTime publishedDate) {
		
		String date = publishedDate.format(LoanDateParser.LOAN_DATE_FORMATTER); 
		date = date.replaceAll("\\+", "%2B");
		date = date.replaceAll(":", "%3A");
		
		String filterByQuery = filterByQuery(DATE_PUBLISHED_FIELD, GREATER_THEN_OPERATION_SUFFIX); 
		
		Map<String,String> params = new HashMap<>();
		params.put(filterByQuery, date);
		
		return params;
	}

	private static String formatUrl(Map<String, String> params) {
		
		StringBuilder urlBuilder = new StringBuilder(LoanClient.ZONKY_MARKETPLACE_API_URL);	
		
		urlBuilder.append("?");
		
		params.entrySet().forEach( entry -> {
			urlBuilder.append(entry.getKey())
			.append("=")
			.append(entry.getValue())
			.append("&");
		});
		
		String url = urlBuilder.toString();
		return url.substring(0, url.length()-1);
	}
}
