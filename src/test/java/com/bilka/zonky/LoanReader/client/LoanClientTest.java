package com.bilka.zonky.LoanReader.client;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import com.bilka.zonky.LoanReader.loan.Loan;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@RestClientTest(LoanClient.class)
public class LoanClientTest {

	@Autowired
	private LoanClient loanClient;
	
	@Autowired
	private MockRestServiceServer server;

	@Autowired
	private ObjectMapper mapper;

	private ZonedDateTime now;
	
	@Test
	public void callLatestLoansTest() throws Exception {
		
		now = ZonedDateTime.now();
		Loan loan = setServerResponse(now);        
		
		List<Loan> loans = loanClient.callLatestLoans(now, 10);
		assertEquals(loan, loans.get(0));
	}

	private Loan setServerResponse(ZonedDateTime now) throws URISyntaxException, JsonProcessingException {
		
		Loan loan = new Loan(1L, now);
		List<Loan> list = Arrays.asList(loan);
		
		server.expect(MockRestRequestMatchers.requestTo(getUri(now)))
		          .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
		          .andRespond(MockRestResponseCreators.withSuccess(mapper.writeValueAsString(list), MediaType.APPLICATION_JSON)
		        );
		
		return loan;
		
	}
	
	private URI getUri(ZonedDateTime date) throws URISyntaxException {
		return new URI(LoanClientParameters.getUrl(LoanClientParameters.getParamMapForDate(now)));
	}
}
