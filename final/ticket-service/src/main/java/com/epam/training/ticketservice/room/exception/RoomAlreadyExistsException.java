package com.epam.training.ticketservice.room.exception;

public class RoomAlreadyExistsException extends RuntimeException{
    public RoomAlreadyExistsException(final String name) {
        super("Room already exists with name: " + name);
    }
}
