package com.shire42.wallet.exception;

public class WalletNotFoundException extends Exception {

    public WalletNotFoundException() {
        super("Customer not found");
    }

    public WalletNotFoundException(String message) {
        super(message);
    }

}
