package com.epam.training.ticketservice.screening;

import java.util.Date;
import java.util.List;

public interface ScreeningService {

    List<String> listScreenings();

    void createScreening(String movieTitle, String roomName, Date startTime);

    void deleteScreening(String movieTitle, String roomName, Date startTime);
}
