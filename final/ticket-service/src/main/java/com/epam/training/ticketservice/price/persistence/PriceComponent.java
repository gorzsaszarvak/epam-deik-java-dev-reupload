package com.epam.training.ticketservice.price.persistence;

import com.epam.training.ticketservice.movie.persistence.Movie;
import com.epam.training.ticketservice.room.persistence.Room;
import com.epam.training.ticketservice.screening.persistence.Screening;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceComponent {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private int amount;
    @OneToMany
    @JoinColumn(referencedColumnName = "id")
    private List<Movie> movies;
    @OneToMany
    @JoinColumn(referencedColumnName = "id")
    private List<Room> rooms;
    @OneToMany
    @JoinColumn(referencedColumnName = "id")
    private List<Screening> screenings;
}
