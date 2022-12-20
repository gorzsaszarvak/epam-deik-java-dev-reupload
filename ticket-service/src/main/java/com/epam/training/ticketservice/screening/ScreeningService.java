package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.screening.persistence.Screening;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningService {

    List<Screening> listScreenings();

    void createScreening(String movieTitle, String roomName, LocalDateTime startTime);

    void deleteScreening(String movieTitle, String roomName, LocalDateTime startTime);

    Screening findScreeningByTitleRoomStartTime(String movieTitle, String roomName, LocalDateTime startTime);
}
