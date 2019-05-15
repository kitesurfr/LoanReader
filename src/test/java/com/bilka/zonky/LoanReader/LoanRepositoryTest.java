package com.bilka.zonky.LoanReader;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

import com.bilka.zonky.LoanReader.loan.Loan;

public class LoanRepositoryTest {

	private AtomicLong idGenerator = new AtomicLong();
	
	@Test
	public void addAllTest() {
		
		ZonedDateTime now = ZonedDateTime.now();
		
		List<Loan> list = new ArrayList<>();
		list.add(new Loan(idGenerator.incrementAndGet(), now.minusMinutes(2)));
		list.add(new Loan(idGenerator.incrementAndGet(), now.minusMinutes(1)));
		
		LoanRepository loanRepository = LoanRepository.getRepository();
		loanRepository.addAll(list);
		
		List<Loan> lastLoans = loanRepository.getLastLoans();
		
		assertEquals(lastLoans.size(), 2);
		assertEquals(list, lastLoans);
	}
	
	@Test
	public void getNewLoansTest() {
		
		ZonedDateTime now = ZonedDateTime.now();
		
		List<Loan> list = new ArrayList<>();
		list.add(new Loan(idGenerator.incrementAndGet(), now.minusMinutes(3)));
		list.add(new Loan(idGenerator.incrementAndGet(), now.minusMinutes(2)));
		
		LoanRepository loanRepository = LoanRepository.getRepository();
		loanRepository.addAll(list);
		
		list.clear();
		list.add(new Loan(idGenerator.incrementAndGet(), now.minusMinutes(1)));
		loanRepository.addAll(list);
		
		List<Loan> newLoans = loanRepository.getNewLoans();
		
		assertEquals(1, newLoans.size());
		assertEquals(list.get(0), newLoans.get(0));
	}
	
}
