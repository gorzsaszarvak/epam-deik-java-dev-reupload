package com.epam.training.ticketservice.screening;

import com.epam.training.ticketservice.screening.persistence.Screening;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ScreeningService {

    List<Screening> listScreenings();

    void createScreening(String movieTitle, String roomName, LocalDateTime startTime);

    void deleteScreening(String movieTitle, String roomName, LocalDateTime startTime);

    Screening findScreeningByTitleRoomStartTime(String movieTitle, String roomName, LocalDateTime startTime);
}
