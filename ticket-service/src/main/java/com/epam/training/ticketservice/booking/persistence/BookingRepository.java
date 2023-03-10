package com.epam.training.ticketservice.booking.persistence;

import com.epam.training.ticketservice.screening.persistence.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findBookingsByScreening(Screening screening);
}
