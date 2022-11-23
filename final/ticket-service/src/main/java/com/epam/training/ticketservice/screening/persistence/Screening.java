package com.epam.training.ticketservice.screening.persistence;

import com.epam.training.ticketservice.movie.persistence.Movie;
import com.epam.training.ticketservice.room.persistence.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Screening {

    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private Movie movie;
    @OneToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;
    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
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
