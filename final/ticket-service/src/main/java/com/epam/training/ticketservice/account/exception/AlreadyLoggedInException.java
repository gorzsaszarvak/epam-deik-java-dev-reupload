package com.epam.training.ticketservice.account.exception;

public class AlreadyLoggedInException extends RuntimeException {
    public AlreadyLoggedInException(String activeAccount) {
        super("You are already logged in as '" + activeAccount + "'");
    }
}
