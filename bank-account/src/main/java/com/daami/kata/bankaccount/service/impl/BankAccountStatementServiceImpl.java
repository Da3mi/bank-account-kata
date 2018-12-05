package com.daami.kata.bankaccount.service.impl;

import com.daami.kata.bankaccount.exception.ServiceException;
import com.daami.kata.bankaccount.model.Account;
import com.daami.kata.bankaccount.model.AccountTransaction;
import com.daami.kata.bankaccount.model.Customer;
import com.daami.kata.bankaccount.service.BankAccountStatementService;
import com.daami.kata.bankaccount.utility.BankAccountUtility;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class BankAccountStatementServiceImpl implements BankAccountStatementService {


    @Override
    public String showCsutomerAccountHistory( final Customer bankCustomer ) {

        String result = Optional.ofNullable( bankCustomer )
                                .map( customerO -> new StringBuilder() )
                                .map( stringBuilder -> stringBuilder.append( getAccountHistoryHeader( bankCustomer ) ) )
                                .map( stringBuilder -> stringBuilder.append( getAccountTransactionsDetails( bankCustomer.getAccount() ) ) )
                                .map( StringBuilder::toString )
                                .orElseThrow( () -> new ServiceException( "customer not found to do deposit transaction" ) );

        System.out.println( result );
        return result;
    }

    private String getAccountHistoryHeader( final Customer bankCustomer ) {
        return String.format( ACCOUNT_HEADER_TEMPLATE, bankCustomer.getName(),
                              LocalDate.now(),
                              LocalDate.now().atStartOfDay(),
                              bankCustomer.getAccount().getAccountBalance() );
    }

    private String getAccountTransactionsDetails( final Account account ) {
        return Optional.ofNullable( account.getAccountTransactions() )
                       .flatMap( accountTransactions -> accountTransactions.stream()
                                                                           .map( accountTransaction -> setAccountBalanceAndPrintLine( accountTransaction,account ) )
                                                                           .reduce( String::concat ) )
                       .orElse( null );
    }

    private String setAccountBalanceAndPrintLine(final AccountTransaction accountTransaction, final Account account){
        account.setAccountBalance( BankAccountUtility.computeBalance(account.getAccountBalance(),
                                                                     accountTransaction.getAmount(),
                                                                     accountTransaction.getOperationType() ) );
        return printLine( accountTransaction,account.getAccountBalance( ));
    }

    private String printLine( final AccountTransaction transaction, final BigDecimal balance ) {
        return String.format( ACCOUNT_OPERATION_LINE_TEMPLATE,
                              transaction.getTransactionDate(),
                              transaction.getOperationType().getOperation().concat( transaction.getAmount().toString() ),
                              balance );
    }
}