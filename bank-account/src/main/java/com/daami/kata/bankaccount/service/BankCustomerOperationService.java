package com.daami.kata.bankaccount.service;

import java.math.BigDecimal;

import com.daami.kata.bankaccount.exception.ServiceException;
import com.daami.kata.bankaccount.model.Customer;

public interface BankCustomerOperationService {
	
	public Customer deposit(final Customer customer, final BigDecimal depositTransactionValue) throws ServiceException;
	
	
}
