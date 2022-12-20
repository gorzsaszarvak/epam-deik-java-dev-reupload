package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.screening.exception.NoScreeningsFoundException;
import com.epam.training.ticketservice.screening.exception.ScreeningOverlapsBreakException;
import com.epam.training.ticketservice.screening.exception.TimeFrameNotAvailableException;
import com.epam.training.ticketservice.screening.persistence.Screening;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

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
        } catch (TimeFrameNotAvailableException | ScreeningOverlapsBreakException exception) {
            return exception.getMessage();
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

    @ShellMethod(value = "List screenings", key = "list screenings")
    public String listScreenings() {
        try {
            screeningService.listScreenings().stream()
                .map(Screening::toString)
                .forEach(System.out::println);
            return null;
        } catch (NoScreeningsFoundException exception) {
            return "There are no screenings";
        } catch (Exception exception) {
            return  exception.getMessage();
        }
    }


}
