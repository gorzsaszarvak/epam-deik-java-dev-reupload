package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.price.PriceService;
import com.epam.training.ticketservice.price.exception.PriceComponentNotFoundException;
import com.epam.training.ticketservice.price.persistence.PriceComponent;
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
class PriceCommandHandlerTest {

    @InjectMocks
    PriceCommandHandler priceCommandHandler;

    @Mock
    PriceService priceService;

    private String componentName;
    private String moveTitle;
    private String roomName;
    private String startTime;

    @BeforeEach
    void setUp() {
        componentName = "testComponent";
        moveTitle = "testMovie";
        roomName = "testRoom";
        startTime = "2022-12-01 18:00";
    }

    @Test
    void testUpdateBasePrice() {
        priceCommandHandler.updateBasePrice(1000);

        verify(priceService, times(1)).updateBasePrice(1000);
    }



    @Test
    void testCreatePriceComponent() {
        priceCommandHandler.createPriceComponent(componentName, 1000);

        verify(priceService, times(1)).createPriceComponent(componentName, 1000);
    }


    @Test
    void testAttachPriceComponentToMovie() {
        priceCommandHandler.attachPriceComponentToMovie(componentName, moveTitle);

        verify(priceService, times(1)).attachPriceComponentToMovie(componentName, moveTitle);
    }

    @Test
    void testAttachPriceComponentToMovieHandlesException() {
        doThrow(PriceComponentNotFoundException.class).when(priceService)
            .attachPriceComponentToMovie(anyString(), anyString());
        String expected = "Could not attach price component";

        String actual = priceCommandHandler.attachPriceComponentToMovie(componentName, moveTitle);

        assertTrue(actual.contains(expected));
    }

    @Test
    void testAttachPriceComponentToRoom() {
        priceCommandHandler.attachPriceComponentToRoom(componentName, roomName);

        verify(priceService, times(1)).attachPriceComponentToRoom(componentName, roomName);
    }

    @Test
    void testAttachPriceComponentToRoomHandlesException() {
        doThrow(PriceComponentNotFoundException.class).when(priceService)
            .attachPriceComponentToRoom(anyString(), anyString());
        String expected = "Could not attach price component";

        String actual = priceCommandHandler.attachPriceComponentToRoom(componentName,roomName);

        assertTrue(actual.contains(expected));
    }

    @Test
    void testAttachPriceComponentToScreening() {
        priceCommandHandler.attachPriceComponentToScreening(componentName, moveTitle, roomName, startTime);

        verify(priceService, times(1)).attachPriceComponentToScreening(anyString(), anyString(), anyString(),
            any(LocalDateTime.class));
    }

    @Test
    void testAttachPriceComponentToScreeningHandlesException() {
        doThrow(PriceComponentNotFoundException.class).when(priceService)
            .attachPriceComponentToScreening(anyString(), anyString(), anyString(), any(LocalDateTime.class));
        String expected = "Could not attach price component";

        String actual = priceCommandHandler.attachPriceComponentToScreening(componentName, moveTitle, roomName, startTime);

        assertTrue(actual.contains(expected));
    }
}