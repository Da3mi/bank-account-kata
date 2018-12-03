package com.daami.kata.bankaccount.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.daami.kata.bankaccount.service.BankCustomerOperationService;
import com.daami.kata.bankaccount.service.impl.BankCustomerOperationServiceImpl;

@Configuration
public class BankAccountServiceConfiguration {

	@Bean
	public BankCustomerOperationService bankAccountService() {
		return new BankCustomerOperationServiceImpl();
	}

}
