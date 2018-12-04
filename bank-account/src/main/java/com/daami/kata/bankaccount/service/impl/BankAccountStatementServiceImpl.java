package com.daami.kata.bankaccount.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.util.CollectionUtils;

import com.daami.kata.bankaccount.exception.ServiceException;
import com.daami.kata.bankaccount.model.Account;
import com.daami.kata.bankaccount.model.AccountTransaction;
import com.daami.kata.bankaccount.model.Customer;
import com.daami.kata.bankaccount.model.OperationType;
import com.daami.kata.bankaccount.service.BankCustomerOperationService;

public class BankAccountStatementServiceImpl {
	
	LocalDateTime transactionDateTime = LocalDate.of(2018, 12, 4).atStartOfDay();

	private final static String ACCOUNT_HEADER_TEMPLATE = "%s \n"
			+ "Today: %s \n"
			+ "+--------------------------------------------------------------------+\n"
			+ "		Date	|	Operations	|	Balance \n"
			+ "+--------------------------------------------------------------------+\n"
			+ " %s	|			| %s \n";
			
	
	private final static String ACCOUNT_OPERATION_LINE_TEMPLATE = ""
			+ " %s	| %s			| %s \n";
	
	
	BigDecimal accBalance = BigDecimal.valueOf(0);
	
	public String showCsutomerAccountHistory(final Customer bankCustomer) {

		
		Customer customer = Optional.ofNullable(bankCustomer)
				.orElseThrow(() -> new ServiceException("customer not found to do deposit transaction"));
		
		//BigDecimal accBalance = bankCustomer.getAccount().getAccountBalance();
		
		StringBuilder accountHistory = new StringBuilder();

		String accountHistoryheader = String.format(ACCOUNT_HEADER_TEMPLATE, bankCustomer.getName(), LocalDate.now(),
				LocalDate.now().atStartOfDay(), bankCustomer.getAccount().getAccountBalance());

		accountHistory.append(accountHistoryheader);

		if (!CollectionUtils.isEmpty(customer.getAccount().getAccountTransactions())) {

			customer.getAccount().getAccountTransactions().forEach(tr -> {
				
				customer.getAccount().setAccountBalance(OperationType.DEPOSIT_OPERATION.equals(tr.getOperationType()) ? 
						customer.getAccount().getAccountBalance().add(tr.getAmount()) : customer.getAccount().getAccountBalance().subtract(tr.getAmount()));

				accountHistory.append(String.format(ACCOUNT_OPERATION_LINE_TEMPLATE, tr.getTransactionDate(),
						tr.getOperationType().getOperation().concat(tr.getAmount().toString()), customer.getAccount().getAccountBalance()));

			});

		}
		System.out.println(accountHistory);
		return accountHistory.toString();
	}
}
