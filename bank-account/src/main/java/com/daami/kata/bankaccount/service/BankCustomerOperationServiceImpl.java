package com.daami.kata.bankaccount.service;

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

public class BankCustomerOperationServiceImpl {

	LocalDateTime transactionDateTime = LocalDate.of(2018, 10, 1).atStartOfDay();

	public Customer deposit(final Customer customer, final BigDecimal depositValue) {
		return Optional.ofNullable(customer)
					.map(customerO -> createNewCustomer(customerO, depositValue))
					.orElseThrow(() -> new ServiceException(""));
	}

	private Customer createNewCustomer(final Customer actualCustomer, final BigDecimal depositValue) {
		return Customer.builder()
					.name(actualCustomer.getName())
					.id(actualCustomer.getId())
					.account(createNewClientAccount(depositValue, actualCustomer.getAccount())).build();
	}

	private Account createNewClientAccount(final BigDecimal depositValue, final Account account) {
		return Account.builder()
					.accountId(account.getAccountId())
					.accountBalance(account.getAccountBalance().add(depositValue))
					.accountTransactions(createNewListOfTransaction(account.getAccountTransactions(), depositValue))
				.	build();
	}

	private List<AccountTransaction> createNewListOfTransaction(List<AccountTransaction> accountTransactions,
			final BigDecimal depositValueOfNewTransaction) {

		return Stream
				.concat(accountTransactions.stream(),
						Arrays.asList(createNewDepositTransaction(depositValueOfNewTransaction)).stream())
				.distinct().collect(Collectors.toList());
	}

	private AccountTransaction createNewDepositTransaction(final BigDecimal depositValue) {
		return AccountTransaction.builder()
								.amount(depositValue)
								.operationType(OperationType.DEPOSIT_OPERATION)
								.transactionDate(transactionDateTime).build();
	}
}