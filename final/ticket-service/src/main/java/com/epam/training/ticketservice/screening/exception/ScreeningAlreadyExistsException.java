package com.epam.training.ticketservice.screening.exception;

public class ScreeningAlreadyExistsException extends RuntimeException {
    public ScreeningAlreadyExistsException() {
        super("A screening already exists with these parameters");
    }
}
