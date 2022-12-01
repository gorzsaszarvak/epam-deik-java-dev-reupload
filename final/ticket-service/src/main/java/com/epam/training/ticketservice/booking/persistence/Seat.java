package com.epam.training.ticketservice.booking.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {
    int row;
    int column;

    @Override
    public String toString() {
        return String.format("%1$d,%2$d", row, column);
    }
}
