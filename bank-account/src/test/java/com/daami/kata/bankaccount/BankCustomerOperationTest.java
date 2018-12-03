package com.daami.kata.bankaccount;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.daami.kata.bankaccount.model.Account;
import com.daami.kata.bankaccount.model.AccountTransaction;
import com.daami.kata.bankaccount.model.Customer;
import com.daami.kata.bankaccount.model.OperationType;
import com.daami.kata.bankaccount.service.impl.BankAccountStatementServiceImpl;
import com.daami.kata.bankaccount.service.impl.BankCustomerOperationServiceImpl;

@SpringBootTest
public class BankCustomerOperationTest {

	private final static String ACCOUNT_HEADER_TEMPLATE = "%s \n Today: %s \n \n"
			+ "+-----------------------------------------------------------+\n"
			+ "		Date		|  		Operations		| 		Balance		\n"
			+ "+-----------------------------------------------------------+\n"
			+ "											  %s				\n";
	
	private final static String ACCOUNT_OPERATION_LINE_TEMPLATE = ""
			+ " %s				| %s 					| %s		\n";
	
	
	@Test
	public void customer_should_do_deposit_operation() {

		LocalDateTime transactionDateTime = LocalDate.of(2018, 10, 1).atStartOfDay();
		BigDecimal depositValue = BigDecimal.valueOf(1000);

		BankCustomerOperationServiceImpl bankCustomerOperationServiceImpl = new BankCustomerOperationServiceImpl();

		Account account = Account.builder()
									.accountId("1l")
									.accountBalance( BigDecimal.valueOf( 0 ) )
									.build();
		
		Customer customer = Customer.builder()
									.account(account)
									.name("Montassar")
									.id(new Long(1)).build();
		
		Customer customerWhoDidDepositOperation = bankCustomerOperationServiceImpl.deposit(customer, depositValue);
		
		AccountTransaction expectedAccountTransaction = AccountTransaction.builder()
																	.transactionDate(transactionDateTime)
																	.amount(depositValue)
																	.operationType(OperationType.DEPOSIT_OPERATION).build();
		
		Account expectedAccount = Account.builder()
										.accountId("1l")
										.accountTransactions(Arrays.asList(expectedAccountTransaction))
										.accountBalance(depositValue).build();
		
		Customer expectedCustomer = Customer.builder()
										.account(expectedAccount)
										.name("Montassar")
										.id(new Long(1)).build();
		
		assertThat(expectedCustomer).isEqualTo(customerWhoDidDepositOperation);

	}

	
	@Test
	public void customer_should_do_withdraw_operation() {
		
		LocalDateTime transactionDateTime = LocalDate.of(2018, 10, 1).atStartOfDay();
		BigDecimal withdrawValue = BigDecimal.valueOf(250);

		BankCustomerOperationServiceImpl bankCustomerOperationServiceImpl = new BankCustomerOperationServiceImpl();
		
		Account account = Account.builder()
								.accountId("1l")
								.accountBalance( BigDecimal.valueOf( 100 ) )
								.build();

		Customer customer = Customer.builder()
									.account(account)
									.name("Montassar")
									.id(new Long(1))
									.build();

		Customer customerWhoDidWhithdrawOperation = bankCustomerOperationServiceImpl.withdraw(customer, withdrawValue);
		
		AccountTransaction expectedAccountTransaction = AccountTransaction.builder()
																.transactionDate(transactionDateTime)
																.amount(withdrawValue)
																.operationType(OperationType.WITHDRAWAL_OPERATION).build();
		
		Account expectedAccount = Account.builder()
											.accountId("1l")
											.accountTransactions(Arrays.asList(expectedAccountTransaction))
											.accountBalance(account.getAccountBalance().subtract(withdrawValue))
											.build();
		
		Customer expectedCustomer = Customer.builder()
										.account(expectedAccount)
										.name("Montassar")
										.id(new Long(1))
										.build();
		
		assertThat(expectedCustomer).isEqualTo(customerWhoDidWhithdrawOperation);

	}
	
	
	@Test
	public void customer_should_check_operations() {
		
		LocalDateTime transactionDateTime = LocalDate.of(2018, 10, 1).atStartOfDay();
		BigDecimal depositValue = BigDecimal.valueOf(1050);
		BigDecimal withdrawValue = BigDecimal.valueOf(250);
		
		BankAccountStatementServiceImpl bankAccountStatementServiceImpl = new BankAccountStatementServiceImpl();
		
		AccountTransaction depositTransaction = AccountTransaction.builder()
				.transactionDate(transactionDateTime)
				.amount(depositValue)
				.operationType(OperationType.WITHDRAWAL_OPERATION).build();
		
		AccountTransaction withdrawTransaction0 = AccountTransaction.builder()
				.transactionDate(transactionDateTime)
				.amount(withdrawValue)
				.operationType(OperationType.WITHDRAWAL_OPERATION).build();
		
		AccountTransaction withdrawTransaction1 = AccountTransaction.builder()
				.transactionDate(transactionDateTime)
				.amount(withdrawValue)
				.operationType(OperationType.WITHDRAWAL_OPERATION).build();

		Account account = Account.builder()
									.accountId("1l")
									.accountTransactions(Arrays.asList(depositTransaction, withdrawTransaction0, withdrawTransaction1))
									.accountBalance(depositValue.subtract(withdrawValue).subtract(withdrawValue))
									.build();
		
		Customer customer = Customer.builder()
										.account(account)
										.name("Montassar")
										.id(new Long(1))
										.build();
	
		String customerBankAccountHistory = bankAccountStatementServiceImpl.showCsutomerAccountHistory(customer);
		
		
		
		String expectedAccounthistoryheader = String.format(ACCOUNT_HEADER_TEMPLATE , customer.getName(), LocalDate.now(), customer.getAccount().getAccountBalance());
		
		String operation0 = String.format(ACCOUNT_OPERATION_LINE_TEMPLATE , depositTransaction.getTransactionDate(), 
				depositTransaction.getOperationType().getOperation().concat(depositTransaction.getAmount().toString()),
				depositValue);
		
		String operation1 = String.format(ACCOUNT_OPERATION_LINE_TEMPLATE , withdrawTransaction0.getTransactionDate(), 
				withdrawTransaction0.getOperationType().getOperation().concat(withdrawTransaction0.getAmount().toString()),
				depositValue.subtract(withdrawValue));
		
		String operation2 = String.format(ACCOUNT_OPERATION_LINE_TEMPLATE , withdrawTransaction1.getTransactionDate(), 
				withdrawTransaction1.getOperationType().getOperation().concat(withdrawTransaction1.getAmount().toString()),
				depositValue.subtract(withdrawValue).subtract(withdrawValue));
		
		StringBuilder expectedAccounthistory = new StringBuilder(expectedAccounthistoryheader)
				.append(operation0).append(operation1).append(operation2);
		
		System.out.println(expectedAccounthistory.toString());
		
		assertThat(customerBankAccountHistory).isNotBlank();
		assertThat(expectedAccounthistory.toString()).isEqualTo(customerBankAccountHistory);
				
	}
	
}
