package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.booking.persistence.Seat;
import com.epam.training.ticketservice.price.impl.PriceServiceImpl;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.screening.persistence.Screening;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.Date;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class PriceCommandHandler extends HelperMethods {

    private final PriceServiceImpl priceService;
    private final ScreeningService screeningService;
    private Screening screening;

    @ShellMethod(value = "update base price 'price'", key = "update base price")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String updateBasePrice(final int basePrice) {
        priceService.updateBasePrice(basePrice);
        return "Updated base price";
    }

    @ShellMethod(value = "create price component 'componentName' 'amount'", key = "create price component")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String createPriceComponent(String componentName, int amount) {
        try {
            priceService.createPriceComponent(componentName, amount);
            return "Price component created";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    @ShellMethod(value = "attach price component to movie 'componentName' 'movieTitle'", key = "attach price component to movie")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String attachPriceComponentToMovie(String componentName, String movieTitle) {
        try {
            priceService.attachPriceComponentToMovie(componentName, movieTitle);
            return "Price component attached to movie";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    @ShellMethod(value = "attach price component to room 'componentName' 'roomName'", key = "attach price component to room")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String attachPriceComponentToRoom(String componentName, String roomName) {
        try {
            priceService.attachPriceComponentToRoom(componentName, roomName);
            return "Price component attached to room";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }


    @ShellMethod(value = "attach price component to screening 'componentName' 'movieTitle' 'roomName' 'startTime'", key = "attach price component to screening")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String attachPriceComponentToRoom(String componentName, String movieTitle, String roomName,
                                             String startTimeString) {
        try {
            Date startTime = parseStartTime(startTimeString);
            priceService.attachPriceComponentToScreening(componentName, movieTitle, roomName, startTime);
            return "Price component attached to screening";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    @ShellMethod(value = "show price for 'movieTitle' 'roomName' 'startTime' 'seats'", key = "show price for")
    public String showPriceFor(String movieTitle, String roomName, String startTimeString, String seatsString) {
        try {
            Date startTime = parseStartTime(startTimeString);
            List<Seat> seats = parseSeats(seatsString);
            Screening screening = screeningService.findScreeningByTitleRoomStartTime(movieTitle, roomName, startTime);
            int price = priceService.getPrice(screening, seats.size());
            return String.format("The price for this booking would be %d HUF", price);
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

}
