package com.epam.training.ticketservice.screening.persistence;

import com.epam.training.ticketservice.movie.persistence.Movie;
import com.epam.training.ticketservice.room.persistence.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Screening {

    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    private Movie movie;
    @OneToOne
    private Room room;
    private Date startTime;

    public Screening(Movie movie, Room room, Date screeningTime) {
        this.movie = movie;
        this.room = room;
        this.startTime = screeningTime;
    }

    @Override
    public String toString() {
        return movie.toString()
                + ", screened in room " + room.getName() +
                ", at " + startTime;
    }
}