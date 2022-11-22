package com.epam.training.ticketservice.room.exception;

public class NoRoomsFoundException extends RuntimeException {
    public NoRoomsFoundException() {
        super("The search found no rooms.");
    }
}
