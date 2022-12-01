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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.epam.training.ticketservice.movie.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.movie.exception.MovieNotFoundException;
import com.epam.training.ticketservice.movie.exception.NoMoviesFoundException;
import com.epam.training.ticketservice.movie.persistence.MovieRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MovieCommandHandlerTest {

    @InjectMocks
    MovieCommandHandler movieCommandHandler;

    @Mock
    MovieServiceImpl movieService;

    private final String title = "testTitle";

    private final String genre = "testGenre";

    private final int length = 100;


    @Test
    void testCreateMovie() {
        movieCommandHandler.createMovie(title, genre, length);

        verify(movieService, times(1)).createMovie(title, genre, length);
    }

    @Test
    void testCreateMovieHandlesExceptionIfMovieAlreadyExists() {
        doThrow(MovieAlreadyExistsException.class).when(movieService).createMovie(title, genre, length);
        String expected = "Could not create movie";

        var actual = movieCommandHandler.createMovie(title, genre, length);

        assertTrue(actual.contains(expected));
    }

    @Test
    void testUpdateMovie() {
        movieCommandHandler.updateMovie(title, genre, length);

        verify(movieService, times(1)).updateMovie(title, genre, length);
    }

    @Test
    void testUpdateMovieHandlesExceptionIfMovieDoesntExist() {
        doThrow(MovieNotFoundException.class).when(movieService).updateMovie(title, genre, length);
        String expected = "Could not update movie";

        var actual = movieCommandHandler.updateMovie(title, genre, length);

        assertTrue(actual.contains(expected));
    }

    @Test
    void testDeleteMovie() {
        movieCommandHandler.deleteMovie(title);

        verify(movieService, times(1)).deleteMovie(title);
    }

    @Test
    void testDeleteMovieHandlesExceptionIfMovieDoesntExist() {
        doThrow(MovieNotFoundException.class).when(movieService).deleteMovie(title);
        String expected = "Could not delete movie";

        var actual = movieCommandHandler.deleteMovie(title);

        assertTrue(actual.contains(expected));
    }

    @Test
    void testListMovies() {
        movieCommandHandler.listMovies();

        verify(movieService, times(1)).listMovies();
    }

    @Test
    void testListMoviesHandlesExceptionWhenNoMoviesExist() {
        when(movieService.listMovies()).thenThrow(NoMoviesFoundException.class);
        String expected = "There are no movies at the moment";

        String actual = movieCommandHandler.listMovies();

        verify(movieService, times(1)).listMovies();
        assertEquals(expected, actual);


    }
}