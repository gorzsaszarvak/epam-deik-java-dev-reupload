package com.epam.training.ticketservice.movie.impl;

import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.movie.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.movie.exception.MovieNotFoundException;
import com.epam.training.ticketservice.movie.exception.NoMoviesFoundException;
import com.epam.training.ticketservice.movie.persistence.Movie;
import com.epam.training.ticketservice.movie.persistence.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(final MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<String> listMoviesAsString() {
        List<String> moviesAsString = Stream.of(movieRepository.findAll())
            .map(x -> x.toString())
            .collect(Collectors.toList());

        if (!moviesAsString.isEmpty()) {
            return moviesAsString;
        } else {
            throw new NoMoviesFoundException();
        }
    }

    @Override
    public void createMovie(String title, String genre, int movieLength) {
        if (!movieRepository.findMovieByTitle(title).isPresent()) {
            movieRepository.save(new Movie(title, genre, movieLength));
        } else {
            throw new MovieAlreadyExistsException(title);
        }
    }

    @Override
    public void updateMovie(String title, String genre, int movieLength) {
        if (movieRepository.findMovieByTitle(title).isPresent()) {
            movieRepository.delete(movieRepository.findMovieByTitle(title).get());
            createMovie(title, genre, movieLength);
        } else {
            throw new MovieNotFoundException(title);
        }
    }

    @Override
    public void deleteMovie(String title) {
        if (movieRepository.findMovieByTitle(title).isPresent()) {
            movieRepository.delete(movieRepository.findMovieByTitle(title).get());
        } else {
            throw new MovieNotFoundException(title);
        }
    }


}
