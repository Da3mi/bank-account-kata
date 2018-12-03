package com.daami.kata.bankaccount.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.daami.kata.bankaccount.exception.ServiceException;
import com.daami.kata.bankaccount.model.Account;
import com.daami.kata.bankaccount.model.AccountTransaction;
import com.daami.kata.bankaccount.model.Customer;
import com.daami.kata.bankaccount.model.OperationType;
import com.daami.kata.bankaccount.service.BankCustomerOperationService;

public class BankCustomerOperationServiceImpl implements BankCustomerOperationService {

	LocalDateTime transactionDateTime = LocalDate.of(2018, 10, 1).atStartOfDay();

	@Override
	public Customer deposit(final Customer customer, final BigDecimal depositTransactionValue) {
		return Optional.ofNullable(customer)
					.map(customerO -> createNewCustomer(customerO, depositTransactionValue, OperationType.DEPOSIT_OPERATION))
					.orElseThrow(() -> new ServiceException("customer not found to do deposit transaction"));
	}
	
	@Override
	public Customer withdraw(Customer customer, BigDecimal withdrawTransactionValue) {
		return Optional.ofNullable(customer)
				.map(customerO -> createNewCustomer(customerO, withdrawTransactionValue, OperationType.WITHDRAWAL_OPERATION))
				.orElseThrow(() -> new ServiceException("customer not found to do withdraw transaction"));
	}

	private Customer createNewCustomer(final Customer actualCustomer, final BigDecimal transactionAmount, OperationType operationType) {
		return Customer.builder()
					.name(actualCustomer.getName())
					.id(actualCustomer.getId())
					.account(createNewCustomerAccount(transactionAmount, actualCustomer.getAccount(), operationType))
					.build();
	}

	private Account createNewCustomerAccount(final BigDecimal transactionAmount, final Account account, OperationType operationType) {
		return Account.builder()
					.accountId(account.getAccountId())
					.accountBalance(computeBalance(account.getAccountBalance(), transactionAmount, operationType))
					.accountTransactions(createNewListOfTransaction(account.getAccountTransactions(), transactionAmount, operationType))
					.build();
	}
	
	private BigDecimal computeBalance(BigDecimal actualBalance, BigDecimal transactionAmount, OperationType operationType ) {
		return Optional.ofNullable(actualBalance).map( balance -> 
				OperationType.DEPOSIT_OPERATION.equals(operationType) ? balance.add(transactionAmount) : balance.subtract(transactionAmount))
				.orElseThrow(() -> new ServiceException("internal technical error"));
	}
	
	private List<AccountTransaction> createNewListOfTransaction(List<AccountTransaction> accountTransactions,
			final BigDecimal transactionAmount, OperationType operationType) {

		return Stream
				.concat(accountTransactions.stream(),
						Arrays.asList(createNewTransaction(transactionAmount, operationType)).stream())
				.distinct().collect(Collectors.toList());
	}

	private AccountTransaction createNewTransaction(final BigDecimal depositValue, OperationType operationType) {
		return AccountTransaction.builder()
								.amount(depositValue)
								.operationType(operationType)
								.transactionDate(transactionDateTime)
								.build();
	}
	
}