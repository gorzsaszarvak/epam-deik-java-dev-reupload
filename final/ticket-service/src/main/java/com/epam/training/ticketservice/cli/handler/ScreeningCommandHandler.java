package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.screening.persistence.Screening;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ScreeningCommandHandler extends HelperMethods {

    private final ScreeningService screeningService;

    @ShellMethod(value = "Create screening", key = "create screening")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String createScreening(final String movieTitle, final String roomName, final String startTime) {
        try {
            screeningService.createScreening(movieTitle, roomName, parseStartTime(startTime));
            return "Created screening.";
        } catch (Exception exception) {
            return "Could not create screening: " + exception.getMessage();
        }
    }

    @ShellMethod(value = "Delete screening", key = "delete screening")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String deleteScreening(final String movieTitle, final String roomName, final String startTime) {
        try {
            screeningService.deleteScreening(movieTitle, roomName, parseStartTime(startTime));
            return "Screening deleted.";
        } catch (Exception exception) {
            return "Could not delete screening: " + exception.getMessage();
        }
    }

    //TODO(can't print in separate lines)
    @ShellMethod(value = "List screenings", key = "list screenings")
    public String listScreenings() {
        try {
            screeningService.listScreenings().stream()
                .map(Screening::toString)
                .forEach(System.out::println);
            return null;
        } catch (Exception exception) {
            return "There are no screenings at the moment";
        }
    }


}
