package com.daami.kata.bankaccount;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import com.daami.kata.bankaccount.model.Account;
import com.daami.kata.bankaccount.model.AccountTransaction;
import com.daami.kata.bankaccount.model.Customer;
import com.daami.kata.bankaccount.model.OperationType;

public class BankCustomerOperationTest {

	public void customer_should_do_deposit_operation() {

		LocalDateTime transactionDateTime = LocalDate.of(2018, 10, 1).atStartOfDay();
		BigDecimal depositValue = BigDecimal.valueOf(1000);

		BankCustomerOperationServiceImpl bankCustomerOperationServiceImpl = new BankClientOperationServiceImpl();

		Account account = Account.builder()
									.accountId("1l").build();
		
		Customer customer = Customer.builder()
									.account(account)
									.name("Montassar")
									.id(new Long(1)).build();
		
		Customer actualCustomer = bankCustomerOperationServiceImpl.deposit(customer, depositValue);
		
		AccountTransaction expectedAccountTransaction = AccountTransaction.builder()
																	.transactionDate(transactionDateTime)
																	.amount(depositValue)
																	.operationType(OperationType.DEPOSIT_OPERATION).build();
		
		Account expectedAccount = Account.builder()
										.accountId("1l")
										.accountTransactions(Arrays.asList(expectedAccountTransaction))
										.accountBalance(depositValue).build();
		
		Customer expectedCustomer = Customer.builder()
				.account(account)
				.name("Montassar")
				.id(new Long(1)).build();
		
		assertThat(expectedCustomer).isEqualTo(actualCustomer);

	}

}