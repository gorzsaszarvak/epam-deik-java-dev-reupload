package com.epam.training.ticketservice.screening.exception;

public class MovieOrRoomNotFoundException extends RuntimeException{
    public MovieOrRoomNotFoundException(final String movieTitle, final String roomName) {
        super("No screening found with movie: " + movieTitle + " or room: " + roomName);
    }
}
