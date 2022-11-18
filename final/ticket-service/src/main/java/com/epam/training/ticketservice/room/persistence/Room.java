package com.epam.training.ticketservice.room.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private int rows;
    private int columns;

    private int seats = rows * columns;

    public Room(String name, int rows, int columns) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.seats = rows * columns;
    }

    @Override
    public String toString() {
        return "Room " + name + " with "
                + seats + " seats, "
                + rows + " rows and "
                + columns + " columns";
    }
}
