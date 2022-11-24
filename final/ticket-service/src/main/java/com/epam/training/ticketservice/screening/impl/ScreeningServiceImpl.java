package com.epam.training.ticketservice.screening.impl;

import com.epam.training.ticketservice.movie.persistence.Movie;
import com.epam.training.ticketservice.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.room.persistence.Room;
import com.epam.training.ticketservice.room.persistence.RoomRepository;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.screening.exception.MovieOrRoomNotFoundException;
import com.epam.training.ticketservice.screening.exception.NoScreeningsFoundException;
import com.epam.training.ticketservice.screening.exception.ScreeningNotFoundException;
import com.epam.training.ticketservice.screening.exception.ScreeningOverlapsBreakException;
import com.epam.training.ticketservice.screening.exception.TimeFrameNotAvailableException;
import com.epam.training.ticketservice.screening.persistence.Screening;
import com.epam.training.ticketservice.screening.persistence.ScreeningRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ScreeningServiceImpl implements ScreeningService {

    ScreeningRepository screeningRepository;
    MovieRepository movieRepository;
    RoomRepository roomRepository;

    public ScreeningServiceImpl(ScreeningRepository screeningRepository, MovieRepository movieRepository,
                                RoomRepository roomRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<String> listScreenings() {
        List<String> screeningsAsString = Stream.of(screeningRepository.findAll())
            .map(Object::toString)
            .collect(Collectors.toList());
        if (!screeningsAsString.isEmpty()) {
            return screeningsAsString;
        } else {
            throw new NoScreeningsFoundException();
        }
    }

    @Override
    public void createScreening(String movieTitle, String roomName, Date startTime) {
        Optional<Movie> movie = movieRepository.findMovieByTitle(movieTitle);
        Optional<Room> room = roomRepository.findRoomByName(roomName);
        if (movie.isPresent() && room.isPresent()) {
            Screening newScreening = new Screening(movie.get(), room.get(), startTime);
            if (timeFrameIsAvailable(newScreening)) {
                screeningRepository.save(newScreening);
            }
        } else {
            throw new MovieOrRoomNotFoundException(movieTitle, roomName);
        }
    }

    @Override
    public void deleteScreening(String movieTitle, String roomName, Date startTime) {
        Optional<Screening> screeningToDelete = Optional.of(
            new Screening(movieRepository.findMovieByTitle(movieTitle).get(),
                roomRepository.findRoomByName(roomName).get(), startTime));
        if (screeningToDelete.isPresent()) {
            Optional<Screening> screeningInRepo =
                screeningRepository.findScreeningByMovieAndRoomAndStartTime(screeningToDelete.get().getMovie(),
                    screeningToDelete.get().getRoom(), screeningToDelete.get().getStartTime());
            if (screeningInRepo.isPresent()) {
                screeningRepository.deleteById(screeningInRepo.get().getId());
            } else {
                throw new ScreeningNotFoundException();
            }
        } else {
            throw new MovieOrRoomNotFoundException(movieTitle, roomName);
        }
    }

    @Override
    public Optional<Screening> findScreeningByTitleRoomStartTime(String movieTitle, String roomName, Date startTime) {
        Optional<Screening> screening = screeningRepository.findScreeningByMovieAndRoomAndStartTime(
            movieRepository.findMovieByTitle(movieTitle).get(),
            roomRepository.findRoomByName(roomName).get(),
            startTime);

        return screening;
    }

    private boolean timeFrameIsAvailable(Screening newScreening) {
        final long breakLength = 10 * 60000;
        for (Screening screening : screeningRepository.findAll()) {
            if (newScreening.getStartTime().before(screening.getEndTime())
                && newScreening.getEndTime().after(screening.getStartTime())) {

                throw new TimeFrameNotAvailableException(newScreening);

            } else if (newScreening.getEndTime().before(new Date(screening.getEndTime().getTime() + breakLength))
                && newScreening.getEndTime().after(screening.getEndTime())) {

                throw new ScreeningOverlapsBreakException(newScreening);
            }
        }
        return true;
    }
}
