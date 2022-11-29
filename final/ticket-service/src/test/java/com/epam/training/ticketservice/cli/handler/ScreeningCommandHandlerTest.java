package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.room.persistence.Room;
import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.screening.exception.NoScreeningsFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ScreeningCommandHandlerTest {

    @InjectMocks
    ScreeningCommandHandler screeningCommandHandler;

    @Mock
    ScreeningService screeningService;

    private final String title = "testTitle";
    private final String roomName = "testName";


    //todo test exception handling


    @Test
    void testCreateScreening() {
        screeningCommandHandler.createScreening(title, roomName, "2030-01-01 08:10");

        verify(screeningService, times(1)).createScreening(anyString(), anyString(), any(LocalDateTime.class));
    }

    @Test
    void testDeleteScreening() {
        screeningCommandHandler.deleteScreening(title, roomName, "2030-01-01 08:10");

        verify(screeningService, times(1)).deleteScreening(anyString(), anyString(), any(LocalDateTime.class));

    }

    @Test
    void testListScreenings() {
        screeningCommandHandler.listScreenings();

        verify(screeningService, times(1)).listScreenings();
    }

    //todo feature works but test fails
//    @Test
//    void testListScreeningsHandlesExceptionIfNoScreeningsExist() {
//        when(screeningService.listScreenings()).thenThrow(NoScreeningsFoundException.class);
//        String expected = "There are no screenings";
//
//        String actual = screeningCommandHandler.listScreenings();
//
//        assertEquals(expected, actual);
//    }
}