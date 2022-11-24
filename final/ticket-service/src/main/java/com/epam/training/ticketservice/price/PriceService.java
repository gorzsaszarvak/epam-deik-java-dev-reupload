package com.epam.training.ticketservice.price;

import com.epam.training.ticketservice.screening.persistence.Screening;

import java.util.Date;

public interface PriceService {

    int getPrice(Screening screening, int numberOfSeats);

    void updateBasePrice(int basePrice);

    void createPriceComponent(String name, int amount);

    void attachPriceComponentToMovie(String componentName, String movieTitle);

    void attachPriceComponentToRoom(String componentName, String roomName);

    void attachPriceComponentToScreening(String componentName, String movieTitle, String roomName, Date startTime);
}
