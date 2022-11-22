package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.account.AccountService;
import com.epam.training.ticketservice.screening.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ScreeningCommandHandler extends AuthorityChecks{

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
    public String listScreenings(){
        try {
            List<String> screeningsAsString = screeningService.listScreenings();

            StringBuilder stringBuilder = new StringBuilder();
            for(String screening : screeningsAsString) {
                stringBuilder.append(screening).append(System.lineSeparator());
            }
            return  stringBuilder.toString();
        } catch (Exception exception) {
            return "There are no screenings at the moment";
        }
    }

    private Date parseStartTime(String startTime) {
        try {
            Date date = new SimpleDateFormat("yyyy-mm-dd hh:mm").parse(startTime);
            return date;
        } catch (Exception exception) {
            throw new RuntimeException("Invalid date format");
        }
    }
}
