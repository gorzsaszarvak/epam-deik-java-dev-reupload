package com.epam.training.ticketservice.movie.exception;

public class NoMoviesFoundException extends RuntimeException {
    public NoMoviesFoundException() {
        super("The search found no movies.");
    }
}
