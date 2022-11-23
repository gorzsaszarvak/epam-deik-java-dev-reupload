package com.epam.training.ticketservice.booking.exception;

public class SeatsAlreadyBookedException extends Throwable {
    public SeatsAlreadyBookedException(String seat) {
        super(String.format("Seat %s is already taken", seat));
    }
}
