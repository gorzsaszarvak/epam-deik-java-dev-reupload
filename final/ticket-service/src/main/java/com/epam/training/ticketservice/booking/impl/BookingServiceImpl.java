package com.epam.training.ticketservice.booking.impl;

import com.epam.training.ticketservice.account.AccountService;
import com.epam.training.ticketservice.account.persistence.Account;
import com.epam.training.ticketservice.booking.BookingService;
import com.epam.training.ticketservice.booking.exception.SeatDoesNotExistException;
import com.epam.training.ticketservice.booking.exception.SeatsAlreadyBookedException;
import com.epam.training.ticketservice.booking.persistence.Booking;
import com.epam.training.ticketservice.booking.persistence.BookingRepository;
import com.epam.training.ticketservice.price.PriceService;
import com.epam.training.ticketservice.price.impl.PriceServiceImpl;
import com.epam.training.ticketservice.booking.persistence.Seat;
import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.screening.exception.ScreeningNotFoundException;
import com.epam.training.ticketservice.screening.persistence.Screening;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {


    private final AccountService accountService;

    private final ScreeningService screeningService;

    private final PriceService priceService;

    private final BookingRepository bookingRepository;

    public Booking mapToBooking(String movieTitle, String roomName, LocalDateTime startTime, List<Seat> seats)
        throws ScreeningNotFoundException {

        Screening screening = screeningService.findScreeningByTitleRoomStartTime(movieTitle, roomName, startTime);
        Booking booking = Booking.builder()
            .screening(screening)
            .seats(seats)
            .price(priceService.getPrice(movieTitle, roomName, startTime, seats.size()))
            .build();

        Account account =
            accountService.findAccountByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        booking.setAccount(account);
        return booking;
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
            if (seat.getRow() < 0 || seat.getColumn() < 0) {
                throw new SeatDoesNotExistException(seat.toString());
            } else if (seat.getRow() > screening.getRoom().getRows()
                || seat.getColumn() > screening.getRoom().getColumns()) {
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
