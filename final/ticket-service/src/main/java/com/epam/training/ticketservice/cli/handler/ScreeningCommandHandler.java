package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.screening.ScreeningService;
import org.springframework.shell.standard.ShellMethod;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ScreeningCommandHandler {

    private final ScreeningService screeningService;

    public ScreeningCommandHandler(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @ShellMethod(value = "Create screening", key = "create screening")
    //TODO(admin method)
    public String createScreening(final String movieTitle, final String roomName, final String startTime) {
        try {
            screeningService.createScreening(movieTitle, roomName, parseStartTime(startTime));
            return "Created screening.";
        } catch (Exception exception) {
            return "Could not create screening: " + exception.getMessage();
        }
    }

    @ShellMethod(value = "Delete screening", key = "delete screening")
    //TODO(admin method)l
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
            return new SimpleDateFormat("yyyy-mm-dd hh:mm").parse(startTime);
        } catch (Exception exception) {
            throw new RuntimeException("Invalid date format");
        }
    }
}
