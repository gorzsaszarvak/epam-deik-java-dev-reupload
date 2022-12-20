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
import com.epam.training.ticketservice.screening.exception.ScreeningNotFoundException;
import com.epam.training.ticketservice.screening.persistence.Screening;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceComponentRepository priceComponentRepository;
    private final MovieService movieService;
    private final RoomService roomService;
    private final ScreeningService screeningService;

    private int basePrice = 1500;

    @Override
    public int getPrice(String movieTitle, String roomName, LocalDateTime startTime, int numberOfSeats) {
        Screening screening = screeningService.findScreeningByTitleRoomStartTime(movieTitle, roomName, startTime);
        return numberOfSeats * (basePrice + getPriceComponentsSum(screening));
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
    public void attachPriceComponentToMovie(String componentName, String movieTitle)
        throws PriceComponentNotFoundException, MovieNotFoundException {

        PriceComponent priceComponent = findPriceComponentByName(componentName);
        Movie movie = movieService.findMovieByTitle(movieTitle);

        List<Movie> movies = priceComponent.getMovies();
        if (movies == null) {
            movies = new ArrayList<>();
        }
        movies.add(movie);
        priceComponent.setMovies(movies);
        priceComponentRepository.save(priceComponent);

    }

    @Override
    public void attachPriceComponentToRoom(String componentName, String roomName)
        throws PriceComponentNotFoundException, RoomNotFoundException {
        PriceComponent priceComponent = findPriceComponentByName(componentName);
        Room room = roomService.findRoomByName(roomName);
        List<Room> rooms = priceComponent.getRooms();
        if (rooms == null) {
            rooms = new ArrayList<>();
        }
        rooms.add(room);
        priceComponent.setRooms(rooms);
        priceComponentRepository.save(priceComponent);
    }

    @Override
    public void attachPriceComponentToScreening(String componentName, String movieTitle, String roomName,
                                                LocalDateTime startTime)
        throws PriceComponentNotFoundException, ScreeningNotFoundException {

        PriceComponent priceComponent = findPriceComponentByName(componentName);
        Screening screening = screeningService.findScreeningByTitleRoomStartTime(movieTitle, roomName, startTime);

        List<Screening> screenings = priceComponent.getScreenings();
        if (screenings == null) {
            screenings = new ArrayList<>();
        }

        screenings.add(screening);
        priceComponent.setScreenings(screenings);
        priceComponentRepository.save(priceComponent);
    }

    private int getPriceComponentsSum(Screening screening) {
        List<PriceComponent> priceComponents = priceComponentRepository.findAll();

        int sum = 0;

        Optional<PriceComponent> movieBonus = priceComponents.stream()
            .filter(x -> x.getMovies().contains(screening.getMovie()))
            .findAny();

        Optional<PriceComponent> roomBonus = priceComponents.stream()
            .filter(x -> x.getRooms().contains((screening.getRoom())))
            .findAny();

        Optional<PriceComponent> screeningBonus = priceComponents.stream()
            .filter(x -> x.getScreenings().contains(screening))
            .findAny();

        if (movieBonus.isPresent()) {
            sum += movieBonus.get().getAmount();
        }
        if (roomBonus.isPresent()) {
            sum += roomBonus.get().getAmount();
        }
        if (screeningBonus.isPresent()) {
            sum += screeningBonus.get().getAmount();
        }

        return sum;
    }

    @Override
    public PriceComponent findPriceComponentByName(String componentName) throws PriceComponentNotFoundException {
        Optional<PriceComponent> priceComponent = priceComponentRepository.findPriceComponentByName(componentName);
        if (priceComponent.isPresent()) {
            return priceComponent.get();
        } else {
            throw new PriceComponentNotFoundException();
        }
    }


}
