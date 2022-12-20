package com.epam.training.ticketservice.account.exception;

public class AccountDoesntExistException extends RuntimeException {
    public AccountDoesntExistException(String username) {
        super("No account exists with username '" + username + "'");
    }
}
