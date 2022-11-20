package com.epam.training.ticketservice.account.exception;

public class NotLoggedInException extends RuntimeException{
    public NotLoggedInException() {
        super("You are not logged in.");
    }
}
