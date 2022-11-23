package com.epam.training.ticketservice.movie.persistence;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "title", unique = true)
    private String title;
    @Column(name = "genre")
    private String genre;
    @Column(name = "length")
    private int length;

    public Movie(String title, String genre, int length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }

    @Override
    public String toString() {
        return title + " (" + genre + ", " + length + " minutes)";
    }
}
