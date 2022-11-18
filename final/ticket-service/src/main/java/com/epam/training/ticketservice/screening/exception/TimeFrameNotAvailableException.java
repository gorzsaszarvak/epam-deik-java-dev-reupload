package com.epam.training.ticketservice.screening.exception;

public class TimeFrameNotAvailableException extends RuntimeException{
    public TimeFrameNotAvailableException(final String screening) {
        super("The time frame is not available for: " + screening);
    }
}
