package com.bilka.zonky.LoanReader.loan;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Loan {

	private Long id;
	private String name;
	private String story;
	private Integer purpose;
	private Long userId;
	private Integer termInMonths;
	private Float interestRate;
	private Float revenueRate;
	private Integer annuity;
	private Integer premium;
	private String rating;
	private BigDecimal amount;
	private Number remainingInvestment;

	@JsonDeserialize(using = LoanDateDeserializer.class)
	@JsonSerialize(using = LoanDateSerializer.class)
	private ZonedDateTime datePublished;
	
	Loan() {}
	
	public Loan(Long id, ZonedDateTime datePublished) {
		this.id = id;
		this.datePublished = datePublished;
	}

	public Loan(Long id, ZonedDateTime datePublished, BigDecimal amount, String name) {
		this.id = id;
		this.datePublished = datePublished;
		this.amount = amount;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public Integer getPurpose() {
		return purpose;
	}

	public void setPurpose(Integer purpose) {
		this.purpose = purpose;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getTermInMonths() {
		return termInMonths;
	}

	public void setTermInMonths(Integer termInMonths) {
		this.termInMonths = termInMonths;
	}

	public Float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Float interestRate) {
		this.interestRate = interestRate;
	}

	public Float getRevenueRate() {
		return revenueRate;
	}

	public void setRevenueRate(Float revenueRate) {
		this.revenueRate = revenueRate;
	}

	public Integer getAnnuity() {
		return annuity;
	}

	public void setAnnuity(Integer annuity) {
		this.annuity = annuity;
	}

	public Integer getPremium() {
		return premium;
	}

	public void setPremium(Integer premium) {
		this.premium = premium;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Number getRemainingInvestment() {
		return remainingInvestment;
	}

	public void setRemainingInvestment(Number remainingInvestment) {
		this.remainingInvestment = remainingInvestment;
	}

	public ZonedDateTime getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(ZonedDateTime datePublished) {
		this.datePublished = datePublished;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Loan other = (Loan) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
