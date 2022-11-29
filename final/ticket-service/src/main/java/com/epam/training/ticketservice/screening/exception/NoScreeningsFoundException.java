package com.epam.training.ticketservice.screening.exception;

public class NoScreeningsFoundException extends RuntimeException {
    public NoScreeningsFoundException() {
        super("There are no screenings");
    }
}
