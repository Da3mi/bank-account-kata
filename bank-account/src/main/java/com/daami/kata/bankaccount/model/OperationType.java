package com.daami.kata.bankaccount.model;

public enum OperationType {

	DEPOSIT_OPERATION("+"), WITHDRAWAL_OPERATION("-");

    private final String operation;

    OperationType(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
	
	
}
