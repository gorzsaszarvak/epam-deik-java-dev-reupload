package com.epam.training.ticketservice.movie;

import com.epam.training.ticketservice.movie.exception.MovieAlreadyExistsException;
import com.epam.training.ticketservice.movie.exception.MovieNotFoundException;
import com.epam.training.ticketservice.movie.exception.NoMoviesFoundException;
import com.epam.training.ticketservice.movie.impl.MovieServiceImpl;
import com.epam.training.ticketservice.movie.persistence.Movie;
import com.epam.training.ticketservice.movie.persistence.MovieRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @InjectMocks
    MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

    private Movie testMovie;

    @BeforeEach
    void setUp() {
        testMovie = new Movie("testTitle", "testGenre", 100);
    }


    @Test
    void testListMoviesReturnsMovies() {
        List<Movie> expected = List.of(testMovie);

        when(movieRepository.findAll()).thenReturn(expected);
        List<Movie> actual = movieService.listMovies();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testListMoviesThrowsExceptionWhenNoMoviesAreSaved() {
        when(movieRepository.findAll()).thenReturn(Collections.emptyList());

        Assertions.assertThrows(NoMoviesFoundException.class, () -> movieService.listMovies());
    }

    @Test
    void testCreateMovieSavesMovieOnce() {
        movieService.createMovie(testMovie.getTitle(), testMovie.getGenre(), testMovie.getLength());

        verify(movieRepository, times(1)).save(testMovie);
    }

    @Test
    void testCreateMovieDoesntSaveMovieIfItAlreadyExists() {
        when(movieRepository.existsByTitle(anyString())).thenReturn(true);

        Assertions.assertThrows(MovieAlreadyExistsException.class,
            () -> movieService.createMovie(testMovie.getTitle(), testMovie.getGenre(), testMovie.getLength()));
        verify(movieRepository, times(0)).save(any());
    }

    @Test
    void updateMovieDoesntSaveWhenMovieDoesntExist() {
        when(movieRepository.existsByTitle(testMovie.getTitle())).thenReturn(false);

        Assertions.assertThrows(MovieNotFoundException.class,
            () -> movieService.updateMovie(testMovie.getTitle(), testMovie.getGenre(), testMovie.getLength()));
        verify(movieRepository, times(0)).delete(any());
        verify(movieRepository, times(0)).save(any());
    }

    @Test
    void testDeleteMovieDeletesMovieIfItExists() {
        when(movieRepository.existsByTitle(testMovie.getTitle())).thenReturn(true);
        movieService.deleteMovie(testMovie.getTitle());

        verify(movieRepository, times(1)).deleteByTitle(testMovie.getTitle());
    }

    @Test
    void testDeleteMovieThrowsExceptionIfTheMovieDoesntExist() {
        when(movieRepository.existsByTitle(testMovie.getTitle())).thenReturn(false);

        Assertions.assertThrows(MovieNotFoundException.class, () -> movieService.deleteMovie(testMovie.getTitle()));
        verify(movieRepository, times(0)).delete(any());
    }

    @Test
    void findMovieByTitleReturnsMovieIfItExists() {
        when(movieRepository.findMovieByTitle(testMovie.getTitle())).thenReturn(Optional.ofNullable(testMovie));
        Movie returnedMovie = movieService.findMovieByTitle(testMovie.getTitle());

        Assertions.assertEquals(testMovie, returnedMovie);
    }

    @Test
    void findMovieByTitleThrowsExceptionWhenMovieDoesntExist() {
        when(movieRepository.findMovieByTitle(testMovie.getTitle())).thenReturn(Optional.empty());

        Assertions.assertThrows(MovieNotFoundException.class,
            () -> movieService.findMovieByTitle(testMovie.getTitle()));
    }
}