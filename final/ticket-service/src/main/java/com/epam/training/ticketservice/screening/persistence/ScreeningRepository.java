package com.epam.training.ticketservice.screening.persistence;

import com.epam.training.ticketservice.movie.persistence.Movie;
import com.epam.training.ticketservice.room.persistence.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    Optional<Screening> findScreeningByMovieAndRoomAndStartTime(Movie movie, Room room, Date startTime);
}
