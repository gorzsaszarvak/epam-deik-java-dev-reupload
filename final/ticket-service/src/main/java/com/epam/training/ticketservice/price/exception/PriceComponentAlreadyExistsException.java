package com.epam.training.ticketservice.price.exception;

public class PriceComponentAlreadyExistsException extends RuntimeException {
    public PriceComponentAlreadyExistsException() {
        super("Price component already exists with this name");
    }
}
