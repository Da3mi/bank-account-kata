package com.daami.kata.bankaccount.service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import com.daami.kata.bankaccount.exception.ServiceException;
import com.daami.kata.bankaccount.model.Account;
import com.daami.kata.bankaccount.model.AccountTransaction;
import com.daami.kata.bankaccount.model.Customer;
import com.daami.kata.bankaccount.model.OperationType;

public class BankCustomerOperationServiceImpl {

    LocalDateTime transactionDateTime = LocalDate.of( 2018, 10, 1 )
                                                 .atStartOfDay();

    public Customer deposit( final Customer customer, final BigDecimal depositValue ) {
        return Optional.ofNullable( customer )
                       .map( customerO -> Customer.builder()
                                                  .name( customerO.getName() )
                                                  .id( customerO.getId() )
                                                  .account( Account.builder()
                                                                   .accountId( customerO.getAccount()
                                                                                        .getAccountId() )
                                                                   .accountBalance( customerO.getAccount()
                                                                                             .getAccountBalance()
                                                                                             .add( depositValue ) )
                                                                   .accountTransactions( Arrays.asList( AccountTransaction.builder()
                                                                                                                          .amount( depositValue )
                                                                                                                          .operationType(
                                                                                                                                  OperationType.DEPOSIT_OPERATION )
                                                                                                                          .transactionDate(
                                                                                                                                  transactionDateTime )
                                                                                                                          .build() ) )
                                                                   .build() )
                                                  .build() )
                       .orElseThrow( () -> new ServiceException( "" ) );
    }
}