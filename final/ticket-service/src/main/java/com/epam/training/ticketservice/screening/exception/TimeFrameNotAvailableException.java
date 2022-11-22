package com.epam.training.ticketservice.screening.exception;

import com.epam.training.ticketservice.screening.persistence.Screening;

public class TimeFrameNotAvailableException extends RuntimeException {
    public TimeFrameNotAvailableException(final Screening screening) {
        super("The time frame is not available for: " + screening.toString());
    }
}
