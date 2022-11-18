package com.epam.training.ticketservice.movie;

import java.util.List;

public interface MovieService{

    List<String> listMoviesAsString();

    void createMovie(String title, String genre, int movieLength);

    void updateMovie(String title, String genre, int movieLength);

    void deleteMovie(String title);

}
