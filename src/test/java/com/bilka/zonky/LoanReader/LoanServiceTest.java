package com.bilka.zonky.LoanReader;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import com.bilka.zonky.LoanReader.client.LoanClient;
import com.bilka.zonky.LoanReader.client.LoanClientParameters;
import com.bilka.zonky.LoanReader.loan.Loan;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@RestClientTest({LoanService.class, LoanClient.class})
public class LoanServiceTest {

		@Autowired
		private LoanService loanService;

		@Autowired
		private MockRestServiceServer server;

		@Autowired
		private ObjectMapper mapper;
		
		private AtomicLong idGenerator = new AtomicLong();
		
		@Test
		public void getNewLoansTest() throws JsonProcessingException, URISyntaxException {
			
			setRepository();
			ZonedDateTime now = ZonedDateTime.now();
			Loan loan = setServerResponse(now);        
			
			List<Loan> loans = loanService.getNewLoans(now, 10);
			
			assertEquals(loans.size(), 1);
			assertEquals(loan, loans.get(0));
		}
		
		@Test
		public void printNewLoansTest() throws JsonProcessingException, URISyntaxException {
			
			setRepository();
			
			ZonedDateTime now = ZonedDateTime.now();
			setServerResponse(now);        
			
			new LoanPrinter().printHeader();
			loanService.printNewLoans(now);
		}

		private Loan setServerResponse(ZonedDateTime now) throws URISyntaxException, JsonProcessingException {
			
			Loan loan = new Loan(idGenerator.incrementAndGet(), now);
			List<Loan> list = Arrays.asList(loan);
			
			String url = LoanClientParameters.getUrl(LoanClientParameters.getParamMapForDate(now));
			server.expect(requestTo(new URI(url)))
			          .andExpect(method(HttpMethod.GET))
			          .andRespond(withSuccess(mapper.writeValueAsString(list), MediaType.APPLICATION_JSON)
			        );
			
			return loan;
		}

		private void setRepository() {

			ZonedDateTime now = ZonedDateTime.now();
			
			List<Loan> list = new ArrayList<>();
			list.add(new Loan(idGenerator.incrementAndGet(), 
								now.minusMinutes(5), 
								new BigDecimal(150000), 
								"New eqiipment for our house"));
			list.add(new Loan(idGenerator.incrementAndGet(), 
								now.minusMinutes(4),
								new BigDecimal(100000), 
								"New car for my oldest son"));
			
			LoanRepository.getRepository().addAll(list);
		}
}
