package com.bilka.zonky.LoanReader.client;

import static com.bilka.zonky.LoanReader.client.LoanClientParameters.DATE_PUBLISHED_FIELD;
import static com.bilka.zonky.LoanReader.client.LoanClientParameters.HEADER_ORDER_FIELD;
import static com.bilka.zonky.LoanReader.client.LoanClientParameters.HEADER_PAGE_NUMBER_FIELD;
import static com.bilka.zonky.LoanReader.client.LoanClientParameters.HEADER_PAGE_SIZE_FIELD;
import static com.bilka.zonky.LoanReader.client.LoanClientParameters.orderByQuery;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

import com.bilka.zonky.LoanReader.client.LoanClientParameters.ORDER_DIRECTION;
import com.bilka.zonky.LoanReader.loan.Loan;
import com.bilka.zonky.LoanReader.loan.LoanDateParser;

/**
 *  This class represent a client that calls Zonky marketplace API and returns list of loans
 */
@Component
public class LoanClient {

	public static final String ZONKY_MARKETPLACE_API_URL = "https://api.zonky.cz/loans/marketplace";

	private static final Logger log = LoggerFactory.getLogger(LoanClient.class);

	private RestTemplate restTemplate;
	
	public LoanClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	/**
	 * Calls API endpoint and returns all loans published since given {@code publishedSinceDate} 
	 * in descending order of their published date.
	 * 
	 * @param publishedSinceDate
	 * @param limit maximum number of loans returned
	 * @return list of loans published since given {@code publishedSinceDate}
	 */
	public List<Loan> callLatestLoans(ZonedDateTime publishedSinceDate, int limit) { 
		
		log.info("Fetching latest loans from Zonky API ...");
		
		if (publishedSinceDate == null) {
			return Collections.emptyList();
		}
		
		return call(publishedSinceDate, getHeaders(limit));
	}
	
	private HttpHeaders getHeaders(int limit) {
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add(HEADER_ORDER_FIELD, orderByQuery(ORDER_DIRECTION.DESC, DATE_PUBLISHED_FIELD));
	    headers.add(HEADER_PAGE_NUMBER_FIELD, String.valueOf(0));
	    headers.add(HEADER_PAGE_SIZE_FIELD, String.valueOf(limit));
	    
		return headers;
	}

	private List<Loan> call(ZonedDateTime publishedSinceDate, HttpHeaders headers) {
		log.debug("Calling zonky market api ... ");
        
		String date = publishedSinceDate.format(LoanDateParser.LOAN_DATE_FORMATTER);
		String url = LoanClientParameters.getUrl(LoanClientParameters.getParamMapForDateQuery());
		
		DefaultUriBuilderFactory builderFactory = new DefaultUriBuilderFactory();
		builderFactory.setEncodingMode(EncodingMode.VALUES_ONLY);
		URI uri = builderFactory.uriString(url).build(date);
		
        ResponseEntity<List<Loan>> loanListResponse = restTemplate.exchange(
        		uri, 
        			HttpMethod.GET, 
        			new HttpEntity<String>("parameters", headers), 
        			new ParameterizedTypeReference<List<Loan>>() {});
        
        log.debug("Calling zonky market api done.");
        
        return loanListResponse.getBody();
	}

}
