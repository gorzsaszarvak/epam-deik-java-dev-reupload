package com.epam.training.ticketservice.screening.persistence;

import com.epam.training.ticketservice.movie.persistence.Movie;
import com.epam.training.ticketservice.room.persistence.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    Optional<Screening> findScreeningByMovieAndRoomAndStartTime(Movie movie, Room room, LocalDateTime startTime);

    boolean existsByMovieAndRoomAndStartTime(Movie movie, Room room, LocalDateTime startTime);
}
