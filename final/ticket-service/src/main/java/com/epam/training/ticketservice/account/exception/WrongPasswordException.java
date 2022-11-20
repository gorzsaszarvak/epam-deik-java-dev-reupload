package com.epam.training.ticketservice.account.exception;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException(String username) {
        super("Wrong password entered for account '" + username + "'");
    }
}
