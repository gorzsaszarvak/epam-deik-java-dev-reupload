package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.screening.ScreeningService;
import com.epam.training.ticketservice.screening.exception.NoScreeningsFoundException;
import com.epam.training.ticketservice.screening.exception.ScreeningAlreadyExistsException;
import com.epam.training.ticketservice.screening.exception.ScreeningNotFoundException;
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


    @Test
    void testCreateScreening() {
        screeningCommandHandler.createScreening(title, roomName, "2030-01-01 08:10");

        verify(screeningService, times(1)).createScreening(anyString(), anyString(), any(LocalDateTime.class));
    }

    @Test
    void testCreateScreeningHandlesExceptionIfScreeningDoesntExist() {
        doThrow(ScreeningAlreadyExistsException.class).when(screeningService)
            .createScreening(anyString(), anyString(), any(LocalDateTime.class));
        String expected = "Could not create screening";

        var actual = screeningCommandHandler.createScreening(title, roomName, "2030-01-01 08:10");

        assertTrue(actual.contains(expected));

    }

    @Test
    void testDeleteScreening() {
        screeningCommandHandler.deleteScreening(title, roomName, "2030-01-01 08:10");

        verify(screeningService, times(1)).deleteScreening(anyString(), anyString(), any(LocalDateTime.class));

    }

    @Test
    void testDeleteScreeningHandlesExceptionIfScreeningDoesntExist() {
        doThrow(ScreeningNotFoundException.class).when(screeningService)
            .deleteScreening(anyString(), anyString(), any(LocalDateTime.class));
        String expected = "Could not delete screening";

        var actual = screeningCommandHandler.deleteScreening(title, roomName, "2030-01-01 08:10");

        assertTrue(actual.contains(expected));

    }

    @Test
    void testListScreenings() {
        screeningCommandHandler.listScreenings();

        verify(screeningService, times(1)).listScreenings();
    }


    @Test
    void testListScreeningsHandlesExceptionIfNoScreeningsExist() {
        doThrow(NoScreeningsFoundException.class).when(screeningService).listScreenings();
        String expected = "There are no screenings";

        String actual = screeningCommandHandler.listScreenings();

        assertEquals(expected, actual);
    }
}