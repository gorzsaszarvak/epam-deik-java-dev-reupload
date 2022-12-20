package com.epam.training.ticketservice.booking.persistence;

import com.epam.training.ticketservice.account.persistence.Account;
import com.epam.training.ticketservice.screening.persistence.Screening;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "bookings")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @OneToOne
    @JoinColumn(name = "screening_id", referencedColumnName = "id")
    private Screening screening;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Seat> seats;

    @Column(name = "price")
    private int price;

    public Booking(Account account, Screening screening, List<Seat> seats, int price) {
        this.account = account;
        this.screening = screening;
        this.seats = seats;
        this.price = price;
    }

    @Override
    public String toString() {

        String pattern = "yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        StringBuilder sb = new StringBuilder();
        List<String> seatsAsString = seats.stream()
                .map(Seat::toString)
                    .collect(Collectors.toList());

        return sb.append("Seats ")
            .append(String.join(", ", seatsAsString))
            .append(" on ")
            .append(screening.getMovie().getTitle())
            .append(" in room ")
            .append(screening.getRoom().getName())
            .append(" starting at ")
            .append(screening.getStartTime().format(formatter))
            .append(" for ")
            .append(price)
            .append(" HUF")
            .toString();
    }
}
