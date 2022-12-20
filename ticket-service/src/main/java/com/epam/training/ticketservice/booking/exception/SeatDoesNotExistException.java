package com.epam.training.ticketservice.booking.exception;

public class SeatDoesNotExistException extends Throwable {
    public SeatDoesNotExistException(String seat) {
        super(String.format("Seat %s does not exist in this room", seat));
    }
}
