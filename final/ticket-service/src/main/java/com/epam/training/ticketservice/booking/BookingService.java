package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.booking.exception.SeatDoesNotExistException;
import com.epam.training.ticketservice.booking.exception.SeatsAlreadyBookedException;
import com.epam.training.ticketservice.booking.persistence.Booking;
import com.epam.training.ticketservice.booking.persistence.Seat;

import java.util.Date;
import java.util.List;

public interface BookingService {

    Booking book(String movieTitle, String roomName, Date startTime, List<Seat> seats)
        throws SeatsAlreadyBookedException, SeatDoesNotExistException;
}
