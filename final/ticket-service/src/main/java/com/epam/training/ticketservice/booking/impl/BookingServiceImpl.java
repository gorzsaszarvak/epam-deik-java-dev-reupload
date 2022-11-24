package com.epam.training.ticketservice.booking.impl;

import com.epam.training.ticketservice.account.AccountService;
import com.epam.training.ticketservice.booking.BookingService;
import com.epam.training.ticketservice.booking.exception.SeatDoesNotExistException;
import com.epam.training.ticketservice.booking.exception.SeatsAlreadyBookedException;
import com.epam.training.ticketservice.booking.persistence.Booking;
import com.epam.training.ticketservice.booking.persistence.BookingRepository;
import com.epam.training.ticketservice.price.impl.PriceServiceImpl;
import com.epam.training.ticketservice.booking.persistence.Seat;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.screening.exception.ScreeningNotFoundException;
import com.epam.training.ticketservice.screening.persistence.Screening;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private AccountService accountService;
    private ScreeningService screeningService;
    private PriceServiceImpl priceService;
    private final BookingRepository bookingRepository;

    @Override
    public Booking book(String movieTitle, String roomName, Date startTime, List<Seat> seats)
        throws SeatsAlreadyBookedException, SeatDoesNotExistException {
        Optional<Screening>
            screening = screeningService.findScreeningByTitleRoomStartTime(movieTitle, roomName, startTime);

        if(screening.isPresent()) {
            Booking booking = Booking.builder()
                .account(
                    accountService.findAccountByUsername(
                        (SecurityContextHolder.getContext().getAuthentication().getName())))
                .screening(screening.get())
                .seats(seats)
                .price(priceService.getPrice(screening.get(), seats.size()))
                .build();

            areSeatsAvailable(screening.get(), seats);
            doSeatsExist(screening.get(), seats);

            bookingRepository.save(booking);
            return booking;
        } else {
            throw new ScreeningNotFoundException();
        }
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
        List<Seat> alreadyBookedSeats =
            bookingRepository.findBookingByScreening(screening).getSeats().stream().filter(seats::contains).collect(
                Collectors.toList());
        if (alreadyBookedSeats.size() != 0) {
            throw new SeatsAlreadyBookedException(alreadyBookedSeats.get(0).toString());
        }
    }
}
