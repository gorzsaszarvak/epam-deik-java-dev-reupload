package com.epam.training.ticketservice.movie;

import com.epam.training.ticketservice.movie.persistence.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> listMovies();

    void createMovie(String title, String genre, int movieLength);

    void updateMovie(String title, String genre, int movieLength);

    void deleteMovie(String title);

    Movie findMovieByTitle(String title);

}
