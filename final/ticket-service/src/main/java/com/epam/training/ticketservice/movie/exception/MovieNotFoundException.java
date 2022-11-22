package com.epam.training.ticketservice.movie.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(final String title) {
        super("No movie with title: " + title);
    }
}
