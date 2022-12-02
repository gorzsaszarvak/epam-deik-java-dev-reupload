package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.booking.BookingService;
import com.epam.training.ticketservice.booking.exception.SeatDoesNotExistException;
import com.epam.training.ticketservice.booking.exception.SeatsAlreadyBookedException;
import com.epam.training.ticketservice.booking.persistence.Booking;
import com.epam.training.ticketservice.booking.persistence.Seat;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class BookingCommandHandler extends HelperMethods {

    private final BookingService bookingService;

    @ShellMethod(value = "book 'movieTitle' 'roomName' 'startTime' 'seats'", key = "book")
    @ShellMethodAvailability(value = "loggedInAsUser")
    public String book(final String movieTitle, final String roomName, final String startTimeString,
                       final String seatsString) {
        try {
            LocalDateTime startTime = parseStartTime(startTimeString);
            List<Seat> seats = parseSeats(seatsString);
            Booking booking = bookingService.mapToBooking(movieTitle, roomName, startTime, seats);
            bookingService.book(booking);
            return booking.toString();
        } catch (SeatDoesNotExistException | SeatsAlreadyBookedException exception) {
            return exception.getMessage();
        }
    }
}
