package com.daami.kata.bankaccount.utility;

import java.math.BigDecimal;
import java.util.Optional;

import com.daami.kata.bankaccount.exception.ServiceException;
import com.daami.kata.bankaccount.model.OperationType;

public final class BankAccountUtility {

	public static BigDecimal computeBalance(final BigDecimal actualBalance, final BigDecimal transactionAmount, final OperationType operationType ) {
		return Optional.ofNullable(actualBalance).map( balance -> 
				OperationType.DEPOSIT_OPERATION.equals(operationType) ? balance.add(transactionAmount) : balance.subtract(transactionAmount))
				.orElseThrow(() -> new ServiceException("internal technical error"));
	}
	
	
}
