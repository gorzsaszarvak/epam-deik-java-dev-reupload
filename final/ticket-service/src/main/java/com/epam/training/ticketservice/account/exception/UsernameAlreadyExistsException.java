package com.epam.training.ticketservice.account.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String username) {
        super("Account with username '" + username + "' already exists");
    }
}
