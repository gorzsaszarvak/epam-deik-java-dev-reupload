package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.account.AccountService;
import com.epam.training.ticketservice.account.persistence.Account;
import com.epam.training.ticketservice.booking.exception.SeatDoesNotExistException;
import com.epam.training.ticketservice.booking.exception.SeatsAlreadyBookedException;
import com.epam.training.ticketservice.booking.impl.BookingServiceImpl;
import com.epam.training.ticketservice.booking.persistence.Booking;
import com.epam.training.ticketservice.booking.persistence.BookingRepository;
import com.epam.training.ticketservice.booking.persistence.Seat;
import com.epam.training.ticketservice.movie.exception.MovieNotFoundException;
import com.epam.training.ticketservice.movie.persistence.Movie;
import com.epam.training.ticketservice.price.PriceService;
import com.epam.training.ticketservice.room.exception.RoomNotFoundException;
import com.epam.training.ticketservice.room.persistence.Room;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.screening.exception.ScreeningNotFoundException;
import com.epam.training.ticketservice.screening.persistence.Screening;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @InjectMocks
    BookingServiceImpl bookingService;

    @Mock
    BookingRepository bookingRepository;

    @Mock
    AccountService accountService;

    @Mock
    ScreeningService screeningService;

    @Mock
    PriceService priceService;

    private Screening testScreening;

    private Movie testMovie;

    private Room testRoom;

    private LocalDateTime startTime;

    private Account testAccount;

    private List<Seat> testSeats;


    @BeforeEach
    void setUp() {
        testMovie = new Movie("title", "genre", 100);
        testRoom = new Room("name", 10, 10);
        startTime = LocalDateTime.now();
        testScreening = new Screening(testMovie, testRoom, startTime);
        testSeats = List.of(new Seat(1, 2));

        testAccount = Account.builder()
            .username("user")
            .password("user")
            .bookings(List.of())
            .build();
        Authentication authentication = new TestingAuthenticationToken(
            testAccount.getUsername(), testAccount, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }


    @Test
    void testBook() throws SeatDoesNotExistException, SeatsAlreadyBookedException {
        when(screeningService.findScreeningByTitleRoomStartTime(anyString(), anyString(),
            any(LocalDateTime.class))).thenReturn(testScreening);
        when(accountService.findAccountByUsername(anyString())).thenReturn(testAccount);
        when(priceService.getPrice(anyString(), anyString(), any(LocalDateTime.class), anyInt())).thenReturn(1000);

        Booking booking = bookingService.mapToBooking("title", "room", LocalDateTime.now(), testSeats);
        bookingService.book(booking);

        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testBookThrowsExceptionWhenScreeningDoesNotExist() {
        when(screeningService.findScreeningByTitleRoomStartTime(anyString(), anyString(),
            any(LocalDateTime.class))).thenThrow(ScreeningNotFoundException.class);

        assertThrows(ScreeningNotFoundException.class,
            () -> bookingService.mapToBooking("title", "room", LocalDateTime.now(), testSeats));

        verify(bookingRepository, times(0)).save(any(Booking.class));
    }

    @Test
    void testBookThrowsExceptionWhenMovieDoesNotExist() {
        when(screeningService.findScreeningByTitleRoomStartTime(anyString(), anyString(),
            any(LocalDateTime.class))).thenThrow(MovieNotFoundException.class);

        assertThrows(MovieNotFoundException.class,
            () -> bookingService.mapToBooking("title", "room", LocalDateTime.now(), testSeats));

        verify(bookingRepository, times(0)).save(any(Booking.class));
    }

    @Test
    void testBookThrowsExceptionWhenRoomDoesNotExist() {
        when(screeningService.findScreeningByTitleRoomStartTime(anyString(), anyString(),
            any(LocalDateTime.class))).thenThrow(RoomNotFoundException.class);

        assertThrows(RoomNotFoundException.class,
            () -> bookingService.mapToBooking("title", "room", LocalDateTime.now(), testSeats));

        verify(bookingRepository, times(0)).save(any(Booking.class));
    }

    @Test
    void testBookThrowsExceptionWhenSeatsBiggerThanMax() {
        when(screeningService.findScreeningByTitleRoomStartTime(anyString(), anyString(),
            any(LocalDateTime.class))).thenReturn(testScreening);
        when(accountService.findAccountByUsername(anyString())).thenReturn(testAccount);
        when(priceService.getPrice(anyString(), anyString(), any(LocalDateTime.class), anyInt())).thenReturn(1000);

        Booking booking = bookingService.mapToBooking("title", "room", LocalDateTime.now(),
            List.of(new Seat(testRoom.getRows() + 1, testRoom.getColumns() + 1)));
        assertThrows(SeatDoesNotExistException.class,
            () -> bookingService.book(booking));

        verify(bookingRepository, times(0)).save(any(Booking.class));
    }

    @Test
    void testBookThrowsExceptionWhenSeatsSmallerThanMin() {
        when(screeningService.findScreeningByTitleRoomStartTime(anyString(), anyString(),
            any(LocalDateTime.class))).thenReturn(testScreening);
        when(accountService.findAccountByUsername(anyString())).thenReturn(testAccount);
        when(priceService.getPrice(anyString(), anyString(), any(LocalDateTime.class), anyInt())).thenReturn(1000);

        Booking booking =
            bookingService.mapToBooking("title", "room", LocalDateTime.now(), List.of(new Seat(-1, -1)));
        assertThrows(SeatDoesNotExistException.class,
            () -> bookingService.book(booking));

        verify(bookingRepository, times(0)).save(any(Booking.class));
    }

    @Test
    void testBookThrowsExceptionWhenSeatsAlreadyBooked() throws SeatDoesNotExistException, SeatsAlreadyBookedException {
        when(screeningService.findScreeningByTitleRoomStartTime(anyString(), anyString(),
            any(LocalDateTime.class))).thenReturn(testScreening);
        when(accountService.findAccountByUsername(anyString())).thenReturn(testAccount);
        when(priceService.getPrice(anyString(), anyString(), any(LocalDateTime.class), anyInt())).thenReturn(1000);

        Booking booking = bookingService.mapToBooking("title", "room", LocalDateTime.now(), List.of(new Seat(1, 1)));
        when(bookingRepository.findBookingsByScreening(any(Screening.class))).thenReturn(List.of(booking));

        assertThrows(SeatsAlreadyBookedException.class,
            () -> bookingService.book(booking));

        verify(bookingRepository, times(0)).save(any(Booking.class));
    }


}