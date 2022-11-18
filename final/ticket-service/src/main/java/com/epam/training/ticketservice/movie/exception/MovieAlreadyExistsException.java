package com.epam.training.ticketservice.movie.exception;

public class MovieAlreadyExistsException extends RuntimeException{
    public MovieAlreadyExistsException(final String title) {
        super("Movie already exists with title: " + title);
    }
}
