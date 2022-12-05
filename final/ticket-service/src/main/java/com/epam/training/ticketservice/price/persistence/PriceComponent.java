package com.epam.training.ticketservice.price.persistence;

import com.epam.training.ticketservice.movie.persistence.Movie;
import com.epam.training.ticketservice.room.persistence.Room;
import com.epam.training.ticketservice.screening.persistence.Screening;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
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
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "amount")
    private int amount;
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    private List<Movie> movies;
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private List<Room> rooms;
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "screening_id", referencedColumnName = "id")
    private List<Screening> screenings;
}
