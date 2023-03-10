package com.epam.training.ticketservice.movie.impl;

import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.movie.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.movie.exception.MovieNotFoundException;
import com.epam.training.ticketservice.movie.exception.NoMoviesFoundException;
import com.epam.training.ticketservice.movie.persistence.Movie;
import com.epam.training.ticketservice.movie.persistence.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    @Override
    public List<Movie> listMovies() throws NoMoviesFoundException {
        List<Movie> movies = movieRepository.findAll();
        if (!movies.isEmpty()) {
            return movies;
        } else {
            throw new NoMoviesFoundException();
        }
    }

    @Override
    public void createMovie(String title, String genre, int movieLength) {
        if (!movieRepository.existsByTitle(title)) {
            movieRepository.save(new Movie(title, genre, movieLength));
        } else {
            throw new MovieAlreadyExistsException(title);
        }
    }

    @Override
    public void updateMovie(String title, String genre, int movieLength) {
        if (movieRepository.existsByTitle(title)) {
            Movie movie = movieRepository.findMovieByTitle(title).get();
            movieRepository.delete(movie);
            createMovie(title, genre, movieLength);
        } else {
            throw new MovieNotFoundException(title);
        }
    }

    @Override
    public void deleteMovie(String title) {
        if (movieRepository.existsByTitle(title)) {
            Movie movie = findMovieByTitle(title);
            movieRepository.delete(movie);
        } else {
            throw new MovieNotFoundException(title);
        }
    }

    @Override
    public Movie findMovieByTitle(String title) throws MovieNotFoundException {
        Optional<Movie> movie = movieRepository.findMovieByTitle(title);
        if (movie.isPresent()) {
            return movie.get();
        } else {
            throw new MovieNotFoundException(title);
        }

    }


}
