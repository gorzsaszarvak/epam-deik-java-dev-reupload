package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.movie.persistence.Movie;
import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.room.persistence.Room;
import com.epam.training.ticketservice.screening.exception.NoScreeningsFoundException;
import com.epam.training.ticketservice.screening.exception.ScreeningAlreadyExistsException;
import com.epam.training.ticketservice.screening.exception.ScreeningNotFoundException;
import com.epam.training.ticketservice.screening.exception.ScreeningOverlapsBreakException;
import com.epam.training.ticketservice.screening.exception.TimeFrameNotAvailableException;
import com.epam.training.ticketservice.screening.impl.ScreeningServiceImpl;
import com.epam.training.ticketservice.screening.persistence.Screening;
import com.epam.training.ticketservice.screening.persistence.ScreeningRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ScreeningServiceTest {

    @InjectMocks
    ScreeningServiceImpl screeningService;

    @Mock
    ScreeningRepository screeningRepository;

    @Mock
    MovieService movieService;

    @Mock
    RoomService roomService;

    private Screening testScreening;
    private Movie testMovie;
    private Room testRoom;
    private LocalDateTime startTime;


    @BeforeEach
    void setUp() {
        startTime = LocalDateTime.now();
        testMovie = new Movie("title", "genre", 100);
        testRoom = new Room("name", 10, 10);
        testScreening = new Screening(testMovie, testRoom, startTime);
    }

    @Test
    void testListScreenings() {
        List<Screening> expected = List.of(testScreening);

        when(screeningRepository.findAll()).thenReturn(expected);
        List<Screening> actual = screeningService.listScreenings();

        assertEquals(expected, actual);
    }

    @Test
    void testListScreeningsThrowsExceptionWhenNoScreeningsExist() {
        when(screeningRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoScreeningsFoundException.class, () -> screeningService.listScreenings());
    }

    @Test
    void testCreateScreening() {
        when(movieService.findMovieByTitle(anyString())).thenReturn(testMovie);
        when(roomService.findRoomByName(anyString())).thenReturn(testRoom);

        screeningService.createScreening(testMovie.getTitle(), testRoom.getName(), startTime);

        verify(screeningRepository, times(1)).save(any(Screening.class));
    }

    @Test
    void testCreateScreeningThrowsExceptionWhenScreeningExists() {
        when(movieService.findMovieByTitle(anyString())).thenReturn(testMovie);
        when(roomService.findRoomByName(anyString())).thenReturn(testRoom);
        when(screeningRepository.existsByMovieAndRoomAndStartTime(any(Movie.class), any(Room.class),
            any(LocalDateTime.class))).thenReturn(true);

        assertThrows(ScreeningAlreadyExistsException.class,
            () -> screeningService.createScreening(testMovie.getTitle(), testRoom.getName(), startTime));
        verify(screeningRepository, times(0)).save(any(Screening.class));
    }

    @Test
    void testCreateScreeningThrowsExceptionWhenScreeningsOverlap() {
        when(movieService.findMovieByTitle(anyString())).thenReturn(testMovie);
        when(roomService.findRoomByName(anyString())).thenReturn(testRoom);
        when(screeningRepository.findAllByRoom(any(Room.class))).thenReturn(
            List.of(new Screening(testMovie, testRoom, startTime.minusMinutes(1))));

        assertThrows(TimeFrameNotAvailableException.class,
            () -> screeningService.createScreening(testMovie.getTitle(), testRoom.getName(), startTime));
        verify(screeningRepository, times(0)).save(any(Screening.class));
    }

    @Test
    void testCreateScreeningThrowsExceptionWhenScreeningOverlapsBreak() {
        when(movieService.findMovieByTitle(anyString())).thenReturn(testMovie);
        when(roomService.findRoomByName(anyString())).thenReturn(testRoom);
        when(screeningRepository.findAllByRoom(any(Room.class))).thenReturn(
            List.of(new Screening(testMovie, testRoom, startTime.minusMinutes(testMovie.getLength()))));

        assertThrows(ScreeningOverlapsBreakException.class,
            () -> screeningService.createScreening(testMovie.getTitle(), testRoom.getName(), startTime));
        verify(screeningRepository, times(0)).save(any(Screening.class));
    }

    @Test
    void testDeleteScreening() {
        when(movieService.findMovieByTitle(anyString())).thenReturn(testMovie);
        when(roomService.findRoomByName(anyString())).thenReturn(testRoom);
        when(screeningRepository.findScreeningByMovieAndRoomAndStartTime(any(), any(), any())).thenReturn(
            Optional.of(testScreening));

        screeningService.deleteScreening(testMovie.getTitle(), testRoom.getName(), startTime);

        verify(screeningRepository, times(1)).delete(any(Screening.class));
    }

    @Test
    void testDeleteScreeningThrowsExceptionWhenScreeningDoesntExist() {
        when(movieService.findMovieByTitle(anyString())).thenReturn(testMovie);
        when(roomService.findRoomByName(anyString())).thenReturn(testRoom);
        when(screeningRepository.findScreeningByMovieAndRoomAndStartTime(any(), any(), any())).thenReturn(
            Optional.empty());

        assertThrows(ScreeningNotFoundException.class,
            () -> screeningService.deleteScreening(testMovie.getTitle(), testRoom.getName(), startTime));
        verify(screeningRepository, times(0)).delete(any(Screening.class));
    }

    @Test
    void testFindScreeningByTitleRoomStartTime() {
        when(screeningRepository.findScreeningByMovieAndRoomAndStartTime(any(), any(), any())).thenReturn(
            Optional.of(testScreening));
        Screening expected = testScreening;

        var actual =
            screeningService.findScreeningByTitleRoomStartTime(testMovie.getTitle(), testRoom.getName(), startTime);

        assertEquals(expected, actual);
    }

    @Test
    void testFindScreeningByTitleRoomStartTimeThrowsExceptionWhenScreeningDoesntExist() {
        when(screeningRepository.findScreeningByMovieAndRoomAndStartTime(any(), any(), any())).thenReturn(
            Optional.empty());

        assertThrows(ScreeningNotFoundException.class,
            () -> screeningService.findScreeningByTitleRoomStartTime(testMovie.getTitle(), testRoom.getName(),
                startTime));
    }
}