package com.epam.training.ticketservice.screening.exception;

import com.epam.training.ticketservice.screening.persistence.Screening;

public class ScreeningOverlapsBreakException extends RuntimeException {
    public ScreeningOverlapsBreakException(Screening newScreening) {
        super("This would start in the break period after another screening in this room. " + newScreening.toString());
    }
}
