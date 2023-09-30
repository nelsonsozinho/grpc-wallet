package com.shire42.customer.exception;

public class CustomerAlreadyExistsException extends Exception {

    public CustomerAlreadyExistsException() {
        super();
    }

    public CustomerAlreadyExistsException(String message) {
        super(message);
    }

}
