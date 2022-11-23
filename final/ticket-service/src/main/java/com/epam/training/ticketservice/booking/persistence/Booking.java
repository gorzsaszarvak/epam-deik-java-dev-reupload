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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

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

    @ElementCollection
    private List<Seat> seats;

    @Column(name = "price")
    private int price;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<String> seatList = new ArrayList<>();
        seats.forEach(x -> seatList.add(String.format("(%d,%d)", x.getRow(), x.getColumn())));

        return sb.append("Seats ")
            .append(String.join(", ", seatList))
            .append(" on ")
            .append(screening.getMovie().getTitle())
            .append(" in room ")
            .append(screening.getRoom().getName())
            .append(" starting at ")
            .append(screening.getStartTime())
            .append(" for ")
            .append(price)
            .append(" HUF")
            .toString();
    }
}
