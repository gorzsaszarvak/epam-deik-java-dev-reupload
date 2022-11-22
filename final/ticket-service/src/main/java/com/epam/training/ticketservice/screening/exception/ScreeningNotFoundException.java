package com.epam.training.ticketservice.screening.exception;

import com.epam.training.ticketservice.screening.persistence.Screening;

public class ScreeningNotFoundException extends RuntimeException {
    public ScreeningNotFoundException(final Screening screening) {
        super("No screening found with: " + screening.toString());
    }
}