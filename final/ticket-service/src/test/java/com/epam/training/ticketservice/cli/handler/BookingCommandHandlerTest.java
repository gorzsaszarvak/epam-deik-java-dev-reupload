package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.account.AccountService;
import com.epam.training.ticketservice.booking.BookingService;
import com.epam.training.ticketservice.booking.exception.SeatDoesNotExistException;
import com.epam.training.ticketservice.booking.exception.SeatsAlreadyBookedException;
import com.epam.training.ticketservice.booking.persistence.Booking;
import com.epam.training.ticketservice.screening.persistence.Screening;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class BookingCommandHandlerTest {

    @InjectMocks
    BookingCommandHandler bookingCommandHandler;

    @Mock
    BookingService bookingService;

    private String movieTitle;
    private String roomName;
    private LocalDateTime startTime;
    private String testSeats;

    @BeforeEach
    void setUp() {
        movieTitle = "testTitle";
        roomName = "testName";
        startTime = LocalDateTime.now();
        testSeats = "1,2 1,3 1,4";
    }

}