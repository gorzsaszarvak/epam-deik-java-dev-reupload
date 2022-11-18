package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.movie.MovieService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class MovieCommandHandler {

    private final MovieService movieService;

    public MovieCommandHandler(MovieService movieService) {
        this.movieService = movieService;
    }

    @ShellMethod(value = "Create movie", key = "create movie")
    //TODO(admin method)
    public String createMovie(final String title, final String genre, final double movieLength) {

        try {
            movieService.createMovie(title, genre, movieLength);
            return "Created movie: " + title;
        } catch (Exception exception) {
            return "Could not create movie, reason: " + exception.getMessage();
        }
    }

    @ShellMethod(value = "Delete movie", key = "delete movie")
    //TODO(admin method)
    public String deleteMovie(final String title) {
        try {
            movieService.deleteMovie(title);
            return "Deleted movie with title: " + title;
        } catch (Exception exception) {
            return "Could not delete movie, reason: " + exception.getMessage();
        }
    }

    @ShellMethod(value = "Update movie", key = "update movie")
    //TODO(admin method)
    public String updateMovie(final String title, final String genre, final double movieLength) {
        try {
            movieService.updateMovie(title, genre, movieLength);
            return "Updated movie with title: " + title;
        } catch (Exception exception) {
            return "Could not update movie, reason: " + exception.getMessage();
        }
    }

    @ShellMethod(value = "List movies", key = "list movies")
    public String listMovies() {
        try {
            return movieService.listMoviesAsString();
        } catch (Exception exception) {
            return "There are no movies at the moment";
        }
    }

}
