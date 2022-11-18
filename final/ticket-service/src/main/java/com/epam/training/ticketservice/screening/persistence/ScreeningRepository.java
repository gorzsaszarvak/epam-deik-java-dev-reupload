package com.epam.training.ticketservice.screening.persistence;

import com.epam.training.ticketservice.movie.persistence.Movie;
import com.epam.training.ticketservice.room.persistence.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;

public interface ScreeningRepository extends CrudRepository<Screening, Long> {

    Optional<Screening> findScreeningByMovieAndRoomAndStartTime(Movie movie, Room room, Date startTime);
}
