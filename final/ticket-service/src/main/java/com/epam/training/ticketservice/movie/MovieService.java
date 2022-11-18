package com.epam.training.ticketservice.movie;

import com.epam.training.ticketservice.movie.persistence.Movie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface MovieService{

    String listMoviesAsString();

    void createMovie(String title, String genre, double movieLength);

    void updateMovie(String title, String genre, double movieLength);

    void deleteMovie(String title);

}
