package com.daami.kata.bankaccount.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
	
	private String 								accountId;
	private BigDecimal 							accountBalance;
	@Singular private List<AccountTransaction> 	accountTransactions;
	
	
	public Account(String accountId) {
		this.accountId = accountId;
	}
	
}
