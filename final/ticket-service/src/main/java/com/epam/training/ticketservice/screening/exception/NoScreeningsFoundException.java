package com.epam.training.ticketservice.screening.exception;

public class NoScreeningsFoundException extends RuntimeException{
    public NoScreeningsFoundException() {
        super("The search found no screenings");
    }
}
