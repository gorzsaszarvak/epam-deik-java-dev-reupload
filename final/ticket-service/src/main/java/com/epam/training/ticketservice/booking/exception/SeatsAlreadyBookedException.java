package com.epam.training.ticketservice.booking.exception;

public class SeatsAlreadyBookedException extends Throwable {
    public SeatsAlreadyBookedException(String seats) {
        super(String.format("Seat(s) %s already taken", seats));
    }
}
