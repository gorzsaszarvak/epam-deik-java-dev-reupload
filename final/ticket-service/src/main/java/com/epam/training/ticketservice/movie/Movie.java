package com.epam.training.ticketservice.movie;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue
    private long id;
    @Column(unique=true)
    private String name;
    private String genre;
    private double length;

    public Movie(String name, String genre, double length) {
        this.name = name;
        this.genre = genre;
        this.length = length;
    }
}
