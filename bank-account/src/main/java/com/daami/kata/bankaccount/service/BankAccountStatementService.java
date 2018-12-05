package com.daami.kata.bankaccount.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.daami.kata.bankaccount.exception.ServiceException;
import com.daami.kata.bankaccount.model.Customer;

public interface BankAccountStatementService {
	
	LocalDateTime transactionDateTime = LocalDate.of(2018, 12, 4).atStartOfDay();
	
	final static String ACCOUNT_HEADER_TEMPLATE = "%s \n"
			+ "Today: %s \n"
			+ "+--------------------------------------------------------------------+\n"
			+ "		Date	|	Operations	|	Balance \n"
			+ "+--------------------------------------------------------------------+\n"
			+ " %s	|			| %s \n";
			
	
	final static String ACCOUNT_OPERATION_LINE_TEMPLATE = ""
			+ " %s	| %s			| %s \n";

	public String showCsutomerAccountHistory(Customer bankCustomer) throws ServiceException;

}
