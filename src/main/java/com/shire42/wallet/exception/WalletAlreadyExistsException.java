package com.shire42.wallet.exception;

public class WalletAlreadyExistsException extends Exception {

    public WalletAlreadyExistsException() {
        super();
    }

    public WalletAlreadyExistsException(String message) {
        super(message);
    }

}
