package com.bilka.zonky.LoanReader;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.bilka.zonky.LoanReader.loan.Loan;

public class LoanPrinter {

	private static final Logger log = LoggerFactory.getLogger(LoanPrinter.class);
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	public void printLoans(List<Loan> loans) {
		
		if (CollectionUtils.isEmpty(loans)) {
			log.info("No new loans since last check.");
		}
		
		for (Loan loan : loans) {
			prettyPrintLoan(loan);
		}
	}

	public void printHeader() {
		log.info(String.format("%-16s %-10s %-50s", "Published", "Amount(Kc)", "Name"));
	}

	private void prettyPrintLoan(Loan loan) {
		log.info(String.format("%-16s %8s,- %-50s", 
				Optional.ofNullable(loan.getDatePublished()).map( date -> date.format(formatter)).orElse(""), 
				Optional.ofNullable(loan.getAmount()).map( value -> value.longValue()).orElse(0L),
				Optional.ofNullable(loan.getName()).orElse("")));
	}
	
}