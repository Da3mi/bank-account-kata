package com.daami.kata.bankaccount.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.daami.kata.bankaccount.exception.ServiceException;
import com.daami.kata.bankaccount.model.Customer;

public interface BankCustomerOperationService {

	LocalDateTime transactionDateTime = LocalDate.of(2018, 10, 1).atStartOfDay();

	public Customer deposit(final Customer customer, final BigDecimal depositTransactionValue) throws ServiceException;

	public Customer withdraw(Customer customer, BigDecimal withdrawTransactionValue) throws ServiceException;

}
