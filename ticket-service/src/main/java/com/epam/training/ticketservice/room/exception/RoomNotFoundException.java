package com.epam.training.ticketservice.room.exception;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(final String name) {
        super("No room with name: " + name);
    }
}
