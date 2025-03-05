package com.simulacro.bank.handler;

public class BankException extends RuntimeException {

    public BankException(String message) {
        super(message);
    }

    public BankException(String mensagem, Object... params) {
        super(String.format(mensagem, params));
    }
}
