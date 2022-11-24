package com.epam.training.ticketservice.price.impl;

import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.movie.exception.MovieNotFoundException;
import com.epam.training.ticketservice.movie.persistence.Movie;
import com.epam.training.ticketservice.price.PriceService;
import com.epam.training.ticketservice.price.exception.PriceComponentAlreadyExistsException;
import com.epam.training.ticketservice.price.exception.PriceComponentNotFoundException;
import com.epam.training.ticketservice.price.persistence.PriceComponent;
import com.epam.training.ticketservice.price.persistence.PriceComponentRepository;
import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.room.exception.RoomNotFoundException;
import com.epam.training.ticketservice.room.persistence.Room;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.screening.persistence.Screening;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceComponentRepository priceComponentRepository;
    private final MovieService movieService;
    private final RoomService roomService;
    private final ScreeningService screeningService;

    private int basePrice = 1500;

    @Override
    public int getPrice(Screening screening, int numberOfSeats) {
        return numberOfSeats * basePrice;
    }

    @Override
    public void updateBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public void createPriceComponent(String name, int amount) {
        if (priceComponentRepository.findPriceComponentByName(name).isEmpty()) {
            priceComponentRepository.save(PriceComponent.builder()
                .name(name)
                .amount(amount)
                .build());
        } else {
            throw new PriceComponentAlreadyExistsException();
        }
    }

    @Override
    public void attachPriceComponentToMovie(String componentName, String movieTitle) {
        Optional<PriceComponent> priceComponent = priceComponentRepository.findPriceComponentByName(componentName);
        Optional<Movie> movie = movieService.findMovieByTitle(movieTitle);
        if (priceComponent.isPresent()) {
            if (movie.isPresent()) {
                List<Movie> movies = priceComponent.get().getMovies();
                if (movies == null) {
                    movies = new ArrayList<>();
                }
                movies.add(movie.get());
                priceComponent.get().setMovies(movies);
                priceComponentRepository.save(priceComponent.get());
            } else {
                throw new MovieNotFoundException(movieTitle);
            }
        } else {
            throw new PriceComponentNotFoundException();
        }
    }

    @Override
    public void attachPriceComponentToRoom(String componentName, String roomName) {
        Optional<PriceComponent> priceComponent = priceComponentRepository.findPriceComponentByName(componentName);
        Optional<Room> room = roomService.findRoomByName(roomName);
        if (priceComponent.isPresent()) {
            if (room.isPresent()) {
                List<Room> rooms = priceComponent.get().getRooms();
                if (rooms == null) {
                    rooms = new ArrayList<>();
                }
                rooms.add(room.get());
                priceComponent.get().setRooms(rooms);
                priceComponentRepository.save(priceComponent.get());
            } else {
                throw new RoomNotFoundException(roomName);
            }
        } else {
            throw new PriceComponentNotFoundException();
        }
    }

    @Override
    public void attachPriceComponentToScreening(String componentName, String movieTitle, String roomName,
                                                Date startTime) {
        Optional<PriceComponent> priceComponent = priceComponentRepository.findPriceComponentByName(componentName);
        Optional<Screening> screening = screeningService.findScreeningByTitleRoomStartTime(movieTitle, roomName, startTime);
        if (priceComponent.isPresent()) {
            if (screening.isPresent()) {
                List<Screening> screenings = priceComponent.get().getScreenings();
                if (screenings == null) {
                    screenings = new ArrayList<>();
                }
                screenings.add(screening.get());
                priceComponent.get().setScreenings(screenings);
                priceComponentRepository.save(priceComponent.get());
            } else {
                throw new RoomNotFoundException(roomName);
            }
        } else {
            throw new PriceComponentNotFoundException();
        }

    }


}
