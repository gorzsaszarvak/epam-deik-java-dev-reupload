package com.epam.training.ticketservice.booking.impl;

import com.epam.training.ticketservice.account.AccountService;
import com.epam.training.ticketservice.account.persistence.Account;
import com.epam.training.ticketservice.booking.BookingService;
import com.epam.training.ticketservice.booking.exception.SeatDoesNotExistException;
import com.epam.training.ticketservice.booking.exception.SeatsAlreadyBookedException;
import com.epam.training.ticketservice.booking.persistence.Booking;
import com.epam.training.ticketservice.booking.persistence.BookingRepository;
import com.epam.training.ticketservice.movie.exception.MovieNotFoundException;
import com.epam.training.ticketservice.price.PriceService;
import com.epam.training.ticketservice.booking.persistence.Seat;
import com.epam.training.ticketservice.room.exception.RoomNotFoundException;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.screening.exception.ScreeningNotFoundException;
import com.epam.training.ticketservice.screening.persistence.Screening;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {


    private final AccountService accountService;

    private final ScreeningService screeningService;

    private final PriceService priceService;

    private final BookingRepository bookingRepository;

    public Booking mapToBooking(String movieTitle, String roomName, LocalDateTime startTime, List<Seat> seats)
        throws ScreeningNotFoundException, MovieNotFoundException, RoomNotFoundException {

        Screening screening = screeningService.findScreeningByTitleRoomStartTime(movieTitle, roomName, startTime);
        int price = priceService.getPrice(movieTitle, roomName, startTime, seats.size());
        Account account =
            accountService.findAccountByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        return new Booking(account, screening, seats, price);
    }

    @Override
    public void book(Booking booking)
        throws SeatsAlreadyBookedException, SeatDoesNotExistException {

        areSeatsAvailable(booking.getScreening(), booking.getSeats());
        doSeatsExist(booking.getScreening(), booking.getSeats());

        bookingRepository.save(booking);

    }

    private void doSeatsExist(Screening screening, List<Seat> seats) throws SeatDoesNotExistException {
        for (Seat seat : seats) {
            if (seat.getRowIndex() < 1 || seat.getColumnIndex() < 1) {
                throw new SeatDoesNotExistException(seat.toString());
            } else if (seat.getRowIndex() > screening.getRoom().getRows()
                || seat.getColumnIndex() > screening.getRoom().getColumns()) {
                throw new SeatDoesNotExistException(seat.toString());
            }
        }
    }

    private void areSeatsAvailable(Screening screening, List<Seat> seats) throws SeatsAlreadyBookedException {
        List<Seat> overlap =
            bookingRepository.findBookingsByScreening(screening).stream()
                .map(Booking::getSeats)
                .flatMap(List::stream)
                .filter(seats::contains)
                .collect(Collectors.toList());

        List<String> overlapAsStrings = overlap.stream()
            .map(Seat::toString)
            .collect(Collectors.toList());

        if (!overlap.isEmpty()) {
            throw new SeatsAlreadyBookedException(String.join(", ", overlapAsStrings));
        }
    }
}
