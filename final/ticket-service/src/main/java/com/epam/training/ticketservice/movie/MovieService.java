package com.epam.training.ticketservice.movie;

import com.epam.training.ticketservice.movie.persistence.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<String> listMoviesAsString();

    void createMovie(String title, String genre, int movieLength);

    void updateMovie(String title, String genre, int movieLength);

    void deleteMovie(String title);

    Optional<Movie> findMovieByTitle(String title);

}
