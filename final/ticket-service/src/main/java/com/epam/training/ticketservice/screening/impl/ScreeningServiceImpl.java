package com.epam.training.ticketservice.screening.impl;

import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.movie.exception.MovieNotFoundException;
import com.epam.training.ticketservice.movie.persistence.Movie;
import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.room.exception.RoomNotFoundException;
import com.epam.training.ticketservice.room.persistence.Room;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.screening.exception.NoScreeningsFoundException;
import com.epam.training.ticketservice.screening.exception.ScreeningAlreadyExistsException;
import com.epam.training.ticketservice.screening.exception.ScreeningNotFoundException;
import com.epam.training.ticketservice.screening.exception.ScreeningOverlapsBreakException;
import com.epam.training.ticketservice.screening.exception.TimeFrameNotAvailableException;
import com.epam.training.ticketservice.screening.persistence.Screening;
import com.epam.training.ticketservice.screening.persistence.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final MovieService movieService;
    private final RoomService roomService;

    @Override
    public List<Screening> listScreenings() throws NoScreeningsFoundException {
        List<Screening> screenings = screeningRepository.findAll();
        if (!screenings.isEmpty()) {
            return screenings;
        } else {
            throw new NoScreeningsFoundException();
        }
    }

    @Override
    public void createScreening(String movieTitle, String roomName, LocalDateTime startTime)
        throws MovieNotFoundException, RoomNotFoundException, ScreeningAlreadyExistsException,
        TimeFrameNotAvailableException {
        Movie movie = movieService.findMovieByTitle(movieTitle);
        Room room = roomService.findRoomByName(roomName);
        Screening newScreening = new Screening(movie, room, startTime);
        if (!screeningRepository.existsByMovieAndRoomAndStartTime(movie, room, startTime)) {
            if (timeFrameIsAvailable(newScreening)) {
                screeningRepository.save(newScreening);
            }
        } else {
            throw new ScreeningAlreadyExistsException();
        }
    }

    @Override
    public void deleteScreening(String movieTitle, String roomName, LocalDateTime startTime)
        throws MovieNotFoundException, RoomNotFoundException, ScreeningNotFoundException {

        Screening screeningToDelete =
            new Screening(movieService.findMovieByTitle(movieTitle), roomService.findRoomByName(roomName), startTime);

        Optional<Screening> screeningInRepo =
            screeningRepository.findScreeningByMovieAndRoomAndStartTime(screeningToDelete.getMovie(),
                screeningToDelete.getRoom(), screeningToDelete.getStartTime());
        if (screeningInRepo.isPresent()) {
            screeningRepository.delete(screeningInRepo.get());
        } else {
            throw new ScreeningNotFoundException();
        }
    }

    @Override
    public Screening findScreeningByTitleRoomStartTime(String movieTitle, String roomName, LocalDateTime startTime)
        throws MovieNotFoundException, RoomNotFoundException, ScreeningNotFoundException {
        Optional<Screening> screening =
            screeningRepository.findScreeningByMovieAndRoomAndStartTime(movieService.findMovieByTitle(movieTitle),
                roomService.findRoomByName(roomName), startTime);

        if (screening.isPresent()) {
            return screening.get();
        } else {
            throw new ScreeningNotFoundException();
        }
    }

    private boolean timeFrameIsAvailable(Screening newScreening) {
        for (Screening screening : screeningRepository.findAll()) {
            if (newScreening.getStartTime().isBefore(screening.getEndTime())
                && newScreening.getEndTime().isAfter(screening.getStartTime())) {

                throw new TimeFrameNotAvailableException(newScreening);

            } else if (newScreening.getEndTime().isBefore(screening.getEndTime().plusMinutes(10))
                && newScreening.getEndTime().isAfter(screening.getEndTime())) {

                throw new ScreeningOverlapsBreakException(newScreening);
            }
        }
        return true;
    }
}
