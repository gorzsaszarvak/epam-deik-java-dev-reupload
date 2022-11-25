package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.screening.persistence.Screening;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ScreeningService {

    List<String> listScreenings();

    void createScreening(String movieTitle, String roomName, Date startTime);

    void deleteScreening(String movieTitle, String roomName, Date startTime);

    Screening findScreeningByTitleRoomStartTime(String movieTitle, String roomName, Date startTime);
}
