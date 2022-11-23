package com.epam.training.ticketservice.room.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "rows")
    private int rows;
    @Column(name = "columns")
    private int columns;

    @Column(name = "seats")
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
