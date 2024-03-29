package com.example.easymeals.exception;

public class InvalidIdentifierException extends RuntimeException {

    public InvalidIdentifierException(Long id) {
        super("Invalid id: " + id);
    }

    public InvalidIdentifierException() {
        super("Invalid id");
    }

}
