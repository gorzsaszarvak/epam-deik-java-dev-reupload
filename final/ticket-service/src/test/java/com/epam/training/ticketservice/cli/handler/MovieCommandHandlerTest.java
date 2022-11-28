package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.movie.impl.MovieServiceImpl;
import com.epam.training.ticketservice.movie.persistence.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.epam.training.ticketservice.movie.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.movie.exception.MovieNotFoundException;
import com.epam.training.ticketservice.movie.exception.NoMoviesFoundException;
import com.epam.training.ticketservice.movie.persistence.MovieRepository;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class MovieCommandHandlerTest {

    @InjectMocks
    MovieCommandHandler movieCommandHandler;

    @Mock
    MovieServiceImpl movieService;

    @Mock
    MovieRepository movieRepository;

    private Movie testMovie;

    private String title;

    private String genre;

    private int length;

    @BeforeEach
    private void setUp() {
        this.title = "testTitle";
        this.genre = "testGenre";
        this.length = 100;

        this.testMovie = new Movie(title, genre, length);
    }

    @Test
    void testCreateMovie() {
        movieCommandHandler.createMovie(title, genre, length);

        verify(movieService, times(1)).createMovie(title, genre, length);
    }

    @Test
    void testCreateMovieHandlesExceptionIfMovieExists() {
        doThrow(MovieAlreadyExistsException.class).when(movieService).createMovie(title, genre, length);
        movieCommandHandler.createMovie(title, genre, length);

        verify(movieRepository, times(0)).save(any(Movie.class));
    }

    @Test
    void testUpdateMovie() {
        movieCommandHandler.updateMovie(title, genre, length);

        verify(movieService, times(1)).updateMovie(title, genre, length);
    }

    @Test
    void testUpdateMovieHandlesExceptionIfMovieDoesntExist() {
        doThrow(MovieNotFoundException.class).when(movieService).updateMovie(title, genre, length);
        movieCommandHandler.updateMovie(title, genre, length);

        verify(movieRepository, times(0)).save(any(Movie.class));
    }

    @Test
    void testDeleteMovie() {
        movieCommandHandler.deleteMovie(title);

        verify(movieService, times(1)).deleteMovie(title);
    }

    @Test
    void testDeleteMovieHandlesExceptionIfMovieDoesntExist() {
        doThrow(MovieNotFoundException.class).when(movieService).deleteMovie(title);
        movieCommandHandler.deleteMovie(title);

        verify(movieRepository, times(0)).delete(any(Movie.class));
    }

    @Test
    void testListMovies() {
        movieCommandHandler.listMovies();

        verify(movieService, times(1)).listMovies();
    }

    @Test
    void testListMoviesHandlesExceptionWhenNoMoviesExist() {
        doThrow(NoMoviesFoundException.class).when(movieService).listMovies();
        String expected = "There are no movies at the moment";

        String actual = movieCommandHandler.listMovies();

        assertEquals(expected, actual);



    }
}