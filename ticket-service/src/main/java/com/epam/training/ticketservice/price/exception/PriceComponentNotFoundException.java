package com.epam.training.ticketservice.price.exception;

public class PriceComponentNotFoundException extends RuntimeException {
    public PriceComponentNotFoundException() {
        super("Price component not found");
    }
}
