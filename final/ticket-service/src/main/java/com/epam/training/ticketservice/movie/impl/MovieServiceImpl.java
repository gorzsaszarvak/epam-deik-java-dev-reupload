package com.epam.training.ticketservice.movie.impl;

import com.epam.training.ticketservice.movie.MovieService;
import com.epam.training.ticketservice.movie.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.movie.exception.MovieNotFoundException;
import com.epam.training.ticketservice.movie.exception.NoMoviesFoundException;
import com.epam.training.ticketservice.movie.persistence.Movie;
import com.epam.training.ticketservice.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.room.RoomRepository;
import com.epam.training.ticketservice.screening.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;

    @Autowired
    public MovieServiceImpl(final MovieRepository movieRepository,
                            final RoomRepository roomRepository,
                            final ScreeningRepository screeningRepository) {
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.screeningRepository = screeningRepository;
    }

    @Override
    public String listMoviesAsString() {
        StringBuilder moviesAsString = new StringBuilder();
        Stream.of(movieRepository.findAll())
                .map(x -> x.toString() + "\n")
                .forEach(moviesAsString::append);
        if(moviesAsString.toString().length() != 0) {
            return moviesAsString.toString();
        } else {
            throw new NoMoviesFoundException();
        }
    }

    @Override
    public void createMovie(String title, String genre, double movieLength) {
        if(movieRepository.findMovieByTitle(title).isPresent()) {
            throw new MovieAlreadyExistsException(title);
        } else {
            movieRepository.save(new Movie(title, genre, movieLength));
        }
    }

    @Override
    public void updateMovie(String title, String genre, double movieLength) {
        if (movieRepository.findMovieByTitle(title).isPresent()) {
            movieRepository.delete(movieRepository.findMovieByTitle(title).get());
            createMovie(title, genre, movieLength);
        } else {
            throw new MovieNotFoundException(title);
        }
    }

    @Override
    public void deleteMovie(String title) {
        if(movieRepository.findMovieByTitle(title).isPresent()){
            movieRepository.delete(movieRepository.findMovieByTitle(title).get());
        } else {
            throw new MovieNotFoundException(title);
        }
    }


}
