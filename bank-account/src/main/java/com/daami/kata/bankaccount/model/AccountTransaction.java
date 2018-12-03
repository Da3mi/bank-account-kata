package com.daami.kata.bankaccount.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransaction {

	private String 			transactionId;
	private LocalDateTime 	transactionDate;
	private BigDecimal 		amount;
	private OperationType 	operationType;

	public AccountTransaction(String transactionId) {
		this.transactionId = transactionId;
	}

}
