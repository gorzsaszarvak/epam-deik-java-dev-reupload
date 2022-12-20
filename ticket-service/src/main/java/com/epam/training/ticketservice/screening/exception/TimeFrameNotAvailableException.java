package com.epam.training.ticketservice.screening.exception;

import com.epam.training.ticketservice.screening.persistence.Screening;

public class TimeFrameNotAvailableException extends RuntimeException {
    public TimeFrameNotAvailableException() {
        super("There is an overlapping screening");
    }
}
