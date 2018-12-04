package com.daami.kata.bankaccount.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.util.CollectionUtils;

import com.daami.kata.bankaccount.exception.ServiceException;
import com.daami.kata.bankaccount.model.AccountTransaction;
import com.daami.kata.bankaccount.model.Customer;
import com.daami.kata.bankaccount.service.BankAccountStatementService;
import com.daami.kata.bankaccount.utility.BankAccountUtility;

public class BankAccountStatementServiceImpl implements BankAccountStatementService {
	
	@Override
	public String showCsutomerAccountHistory(final Customer bankCustomer) {

		Customer customer = Optional.ofNullable(bankCustomer)
				.orElseThrow(() -> new ServiceException("customer not found to do deposit transaction"));

		StringBuilder accountHistory = new StringBuilder();

		String accountHistoryheader = String.format(ACCOUNT_HEADER_TEMPLATE, bankCustomer.getName(), LocalDate.now(),
				LocalDate.now().atStartOfDay(), bankCustomer.getAccount().getAccountBalance());

		accountHistory.append(accountHistoryheader);

		if (!CollectionUtils.isEmpty(customer.getAccount().getAccountTransactions())) {

			customer.getAccount().getAccountTransactions().forEach(tr -> {

				customer.getAccount().setAccountBalance(BankAccountUtility.computeBalance(
						customer.getAccount().getAccountBalance(), tr.getAmount(), tr.getOperationType()));

				accountHistory.append(printLine(tr, customer.getAccount().getAccountBalance()));

			});

		}
		System.out.println(accountHistory);
		return accountHistory.toString();
	}
	
	private String printLine(final AccountTransaction transaction, final BigDecimal balance) {
		return String.format(ACCOUNT_OPERATION_LINE_TEMPLATE, transaction.getTransactionDate(),
				transaction.getOperationType().getOperation().concat(transaction.getAmount().toString()), balance);
	}
	
	
}
