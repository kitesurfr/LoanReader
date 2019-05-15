package com.bilka.zonky.LoanReader;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.bilka.zonky.LoanReader.loan.Loan;

/**
 * Repository representing a list of loans from last API call. 
 */
public class LoanRepository {

	private static final LoanRepository instance = new LoanRepository();
	
	private List<Loan> lastLoans = Collections.emptyList();
	
	private List<Loan> newLoans = Collections.emptyList();

	private LoanRepository() {}
	
	public static LoanRepository getRepository() {
		return instance;
	}
	
	public void addAll(List<Loan> loans) {
		newLoans = Collections.unmodifiableList(getNewLoans(loans));
		lastLoans = Collections.unmodifiableList(new LinkedList<>(loans));
	}
	
	public List<Loan> getNewLoans() {
		return newLoans;
	}
	
	private List<Loan> getNewLoans(List<Loan> latestLoans) {
		return latestLoans.stream()
				.filter(loan -> !lastLoans.contains(loan))
				.collect(Collectors.toList());
	}

	public List<Loan> getLastLoans() {
		return lastLoans;
	}

}
