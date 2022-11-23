package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.movie.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class MovieCommandHandler extends HelperMethods {

    private final MovieService movieService;

    @ShellMethod(value = "Create movie", key = "create movie")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String createMovie(final String title, final String genre, final int movieLength) {

        try {
            movieService.createMovie(title, genre, movieLength);
            return "Created movie.";
        } catch (Exception exception) {
            return "Could not create movie: " + exception.getMessage();
        }
    }

    @ShellMethod(value = "Update movie", key = "update movie")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String updateMovie(final String title, final String genre, final int movieLength) {
        try {
            movieService.updateMovie(title, genre, movieLength);
            return "Updated movie.";
        } catch (Exception exception) {
            return "Could not update movie: " + exception.getMessage();
        }
    }

    @ShellMethod(value = "Delete movie", key = "delete movie")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String deleteMovie(final String title) {
        try {
            movieService.deleteMovie(title);
            return "Deleted movie.";
        } catch (Exception exception) {
            return "Could not delete movie, reason: " + exception.getMessage();
        }
    }

    //TODO(can't print in separate lines)
    @ShellMethod(value = "List movies", key = "list movies")
    public String listMovies() {
        try {
            List<String> moviesAsString = movieService.listMoviesAsString();

            StringBuilder stringBuilder = new StringBuilder();
            for (String movie : moviesAsString) {
                stringBuilder.append(movie).append(System.lineSeparator());
            }
            return stringBuilder.toString();
        } catch (Exception exception) {
            return "There are no movies at the moment";
        }
    }


}
