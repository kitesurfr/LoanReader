package com.bilka.zonky.LoanReader;

import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bilka.zonky.LoanReader.client.LoanClient;
import com.bilka.zonky.LoanReader.loan.Loan;

@Service
public class LoanService {
	
	private static final Logger log = LoggerFactory.getLogger(LoanPrinter.class);
	
	private LoanClient clientCaller;

	private LoanPrinter loanPrinter = new LoanPrinter();
	
	public LoanService(LoanClient clientCaller) {
		this.clientCaller = clientCaller;
	}

	public void printNewLoans(ZonedDateTime dateTime) {
		List<Loan> loans = getNewLoans(dateTime, Integer.MAX_VALUE);
		loanPrinter.printLoans(loans);
	}

	public void initialLoanPrint() {
		List<Loan> loans = getNewLoans(ZonedDateTime.now().minusYears(10), 10);
		loanPrinter.printHeader();
		loanPrinter.printLoans(loans);
		log.info("This is an initial print (last {} loans printed).", loans.size());
	}
	
	
	public List<Loan> getNewLoans(ZonedDateTime dateTime, int limit) {
		List<Loan> latestLoans = clientCaller.callLatestLoans(dateTime, limit);
		LoanRepository loanRepository = LoanRepository.getRepository();
		loanRepository.addAll(latestLoans);
		return loanRepository.getNewLoans();
	}

}
