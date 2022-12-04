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
    int rowIndex;
    int columnIndex;

    @Override
    public String toString() {
        return String.format("(%d,%d)", rowIndex, columnIndex);
    }
}
