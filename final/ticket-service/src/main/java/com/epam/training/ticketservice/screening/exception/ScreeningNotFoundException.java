package com.epam.training.ticketservice.screening.exception;

import com.epam.training.ticketservice.screening.persistence.Screening;

public class ScreeningNotFoundException extends RuntimeException {
    public ScreeningNotFoundException() {
        super("Screening not found");
    }
}