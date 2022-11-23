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
    private int row;
    private int column;

    @Override
    public String toString() {
        return String.format("(%r,%c)", row, column);
    }
}
