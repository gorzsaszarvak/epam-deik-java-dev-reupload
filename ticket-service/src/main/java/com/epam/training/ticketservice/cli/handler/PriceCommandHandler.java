package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.booking.persistence.Seat;
import com.epam.training.ticketservice.price.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class PriceCommandHandler extends HelperMethods {

    private final PriceService priceService;

    @ShellMethod(value = "update base price 'price'", key = "update base price")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String updateBasePrice(final int basePrice) {
        priceService.updateBasePrice(basePrice);
        return "Updated base price";
    }

    @ShellMethod(value = "create price component 'componentName' 'amount'",
        key = "create price component")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String createPriceComponent(String componentName, int amount) {
        try {
            priceService.createPriceComponent(componentName, amount);
            return "Price component created";
        } catch (Exception exception) {
            return "Could not create price component: " + exception.getMessage();
        }
    }

    @ShellMethod(value = "attach price component to movie 'componentName' 'movieTitle'",
        key = "attach price component to movie")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String attachPriceComponentToMovie(String componentName, String movieTitle) {
        try {
            priceService.attachPriceComponentToMovie(componentName, movieTitle);
            return "Price component attached to movie";
        } catch (Exception exception) {
            return "Could not attach price component: " + exception.getMessage();
        }
    }

    @ShellMethod(value = "attach price component to room 'componentName' 'roomName'",
        key = "attach price component to room")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String attachPriceComponentToRoom(String componentName, String roomName) {
        try {
            priceService.attachPriceComponentToRoom(componentName, roomName);
            return "Price component attached to room";
        } catch (Exception exception) {
            return "Could not attach price component: " + exception.getMessage();
        }
    }


    @ShellMethod(value = "attach price component to screening 'componentName' 'movieTitle' 'roomName' 'startTime'",
        key = "attach price component to screening")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String attachPriceComponentToScreening(String componentName, String movieTitle, String roomName,
                                             String startTimeString) {
        try {
            LocalDateTime startTime = parseStartTime(startTimeString);
            priceService.attachPriceComponentToScreening(componentName, movieTitle, roomName, startTime);
            return "Price component attached to screening";
        } catch (Exception exception) {
            return "Could not attach price component: " + exception.getMessage();
        }
    }

    @ShellMethod(value = "show price for 'movieTitle' 'roomName' 'startTime' 'seats'",
        key = "show price for")
    public String showPriceFor(String movieTitle, String roomName, String startTimeString, String seatsString) {
        try {
            LocalDateTime startTime = parseStartTime(startTimeString);
            List<Seat> seats = parseSeats(seatsString);
            int price = priceService.getPrice(movieTitle, roomName, startTime, seats.size());
            return String.format("The price for this booking would be %d HUF", price);
        } catch (Exception exception) {
            return "Could not calculate price: " + exception.getMessage();
        }
    }

}
