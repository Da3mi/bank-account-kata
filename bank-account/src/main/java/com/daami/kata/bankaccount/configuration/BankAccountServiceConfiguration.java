package com.daami.kata.bankaccount.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.daami.kata.bankaccount.service.BankAccountStatementService;
import com.daami.kata.bankaccount.service.BankCustomerOperationService;
import com.daami.kata.bankaccount.service.impl.BankAccountStatementServiceImpl;
import com.daami.kata.bankaccount.service.impl.BankCustomerOperationServiceImpl;

@Configuration
public class BankAccountServiceConfiguration {

	@Bean
	public BankCustomerOperationService BankCustomerOperationService() {
		return new BankCustomerOperationServiceImpl();
	}
	
	@Bean
	public BankAccountStatementService bankAccountService() {
		return new BankAccountStatementServiceImpl();
	}
	

}
