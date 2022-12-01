package com.epam.training.ticketservice.price;

import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.movie.persistence.Movie;
import com.epam.training.ticketservice.price.exception.PriceComponentAlreadyExistsException;
import com.epam.training.ticketservice.price.exception.PriceComponentNotFoundException;
import com.epam.training.ticketservice.price.impl.PriceServiceImpl;
import com.epam.training.ticketservice.price.persistence.PriceComponent;
import com.epam.training.ticketservice.price.persistence.PriceComponentRepository;
import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.room.persistence.Room;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.screening.persistence.Screening;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


//todo more tests
@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @InjectMocks
    PriceServiceImpl priceService;

    @Mock
    MovieService movieService;
    @Mock
    RoomService roomService;
    @Mock
    ScreeningService screeningService;
    @Mock
    PriceComponentRepository priceComponentRepository;

    private Movie testMovie;
    private Room testRoom;
    private Screening testScreening;
    private LocalDateTime startTime;

    private PriceComponent testComponent;

    @BeforeEach
    void setUp() {
        startTime = LocalDateTime.now();
        testMovie = new Movie("testTitle", "testGenre", 100);
        testRoom = new Room("testName", 10, 10);
        testScreening = new Screening(testMovie, testRoom, startTime);
        testComponent = PriceComponent.builder()
            .name("testComponent")
            .amount(1000)
            .build();
    }

    @Test
    void testGetPrice() {
        when(priceComponentRepository.findAll()).thenReturn(Collections.emptyList());
        int expected = 1500;

        int actual = priceService.getPrice(testMovie.getTitle(), testRoom.getName(), startTime, 1);

        assertEquals(expected, actual);
    }


    @Test
    void testUpdateBasePrice() {
        when(priceComponentRepository.findAll()).thenReturn(Collections.emptyList());
        int expected = 3000;

        priceService.updateBasePrice(3000);
        var actual = priceService.getPrice(testMovie.getTitle(), testRoom.getName(), startTime, 1);

        assertEquals(expected, actual);
    }

    @Test
    void testCreatePriceComponent() {
        when(priceComponentRepository.findPriceComponentByName(anyString())).thenReturn(Optional.empty());

        priceService.createPriceComponent("testComponent", 1000);

        verify(priceComponentRepository, times(1)).save(any(PriceComponent.class));
    }

    @Test
    void testCreatePriceComponentThrowsExceptionWhenComponentAlreadyExists() {
        when(priceComponentRepository.findPriceComponentByName(anyString())).thenReturn(Optional.of(testComponent));

        assertThrows(PriceComponentAlreadyExistsException.class,
            () -> priceService.createPriceComponent("testComponent", 1000));
        verify(priceComponentRepository, times(0)).save(any(PriceComponent.class));
    }

    @Test
    void testAttachPriceComponentToMovie() {
        when(priceComponentRepository.findPriceComponentByName(anyString())).thenReturn(Optional.of(testComponent));
        when(movieService.findMovieByTitle(anyString())).thenReturn(testMovie);

        priceService.attachPriceComponentToMovie(testComponent.getName(), testMovie.getTitle());

        verify(priceComponentRepository, times(1)).save(any(PriceComponent.class));
    }

    @Test
    void testAttachPriceComponentToMovieThrowsExceptionWhenComponentDoesntExist() {
        when(priceComponentRepository.findPriceComponentByName(anyString())).thenReturn(Optional.empty());

        assertThrows(PriceComponentNotFoundException.class,
            () -> priceService.attachPriceComponentToMovie(testComponent.getName(), testMovie.getTitle()));
        verify(priceComponentRepository, times(0)).save(any(PriceComponent.class));
    }

    @Test
    void testAttachPriceComponentToRoom() {
        when(priceComponentRepository.findPriceComponentByName(anyString())).thenReturn(Optional.of(testComponent));
        when(roomService.findRoomByName(anyString())).thenReturn(testRoom);

        priceService.attachPriceComponentToRoom(testComponent.getName(), testRoom.getName());

        verify(priceComponentRepository, times(1)).save(any(PriceComponent.class));
    }

    @Test
    void testAttachPriceComponentToRoomThrowsExceptionWhenComponentDoesntExist() {
        when(priceComponentRepository.findPriceComponentByName(anyString())).thenReturn(Optional.empty());

        assertThrows(PriceComponentNotFoundException.class,
            () -> priceService.attachPriceComponentToRoom(testComponent.getName(), testRoom.getName()));
        verify(priceComponentRepository, times(0)).save(any(PriceComponent.class));
    }

    @Test
    void testAttachPriceComponentToScreening() {
        when(priceComponentRepository.findPriceComponentByName(anyString())).thenReturn(Optional.of(testComponent));
        when(screeningService.findScreeningByTitleRoomStartTime(testMovie.getTitle(), testRoom.getName(),
            startTime)).thenReturn(testScreening);

        priceService.attachPriceComponentToScreening(testComponent.getName(), testMovie.getTitle(), testRoom.getName(), startTime);

        verify(priceComponentRepository, times(1)).save(any(PriceComponent.class));
    }

    @Test
    void testAttachPriceComponentToScreeningThrowsExceptionWhenComponentDoesntExist() {
        when(priceComponentRepository.findPriceComponentByName(anyString())).thenReturn(Optional.empty());

        assertThrows(PriceComponentNotFoundException.class,
            () -> priceService.attachPriceComponentToScreening(testComponent.getName(), testMovie.getTitle(), testRoom.getName(), startTime));
        verify(priceComponentRepository, times(0)).save(any(PriceComponent.class));
    }

    @Test
    void testFindPriceComponentByName() {
        when(priceComponentRepository.findPriceComponentByName(anyString())).thenReturn(Optional.of(testComponent));
        PriceComponent expected = testComponent;

        var actual = priceService.findPriceComponentByName(testComponent.getName());

        assertEquals(expected, actual);
    }
}