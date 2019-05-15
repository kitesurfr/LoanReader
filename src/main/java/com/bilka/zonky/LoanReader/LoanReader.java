package com.bilka.zonky.LoanReader;

import java.time.ZonedDateTime;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *  This class initialize a periodic call of {@link LoanService#printNewLoans(ZonedDateTime)}.
 */
@Component
public class LoanReader {
	
	private static final Logger log = LoggerFactory.getLogger(LoanReader.class);

	private static final int MINUTES = 5;

	private LoanService loanService;

	public LoanReader(LoanService loanService) {
		this.loanService = loanService;
	}
	
	@PostConstruct
	private void initialLoanPrint() {
		loanService.initialLoanPrint();
		log.info("Waiting {} minutes before new loans will be checked.", MINUTES);
	}
	
	/**
	 * Prints new loans every 5 minutes with initial 5 minute delay.
	 */
	@Scheduled(fixedRate = MINUTES * 1000 * 60, initialDelay = MINUTES * 1000 * 60)
	public void init() {
		loanService.printNewLoans(ZonedDateTime.now().minusMinutes(MINUTES));
		log.info("Waiting {} minutes before new check.", MINUTES);
	}

}
