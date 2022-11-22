package com.epam.training.ticketservice.screening.persistence;

import com.epam.training.ticketservice.movie.persistence.Movie;
import com.epam.training.ticketservice.room.persistence.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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

    private Date endTime;

    public Screening(Movie movie, Room room, Date screeningTime) {
        this.movie = movie;
        this.room = room;
        this.startTime = screeningTime;
        this.endTime = new Date(startTime.getTime() + (movie.getLength() * 60000L));
    }

    @Override
    public String toString() {
        return movie.toString()
            + ", screened in room " + room.getName()
            + ", at " + startTime;
    }
}
