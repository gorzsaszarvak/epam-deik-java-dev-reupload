package com.epam.training.ticketservice.screening.exception;

public class ScreeningNotFoundException extends RuntimeException{
    public ScreeningNotFoundException(final String screening) {
        super("No screening found with: " + screening);
    }
}