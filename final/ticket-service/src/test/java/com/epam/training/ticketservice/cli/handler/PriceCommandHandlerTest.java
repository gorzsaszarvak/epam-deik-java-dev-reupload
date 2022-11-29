package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.price.PriceService;
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

    @Test
    void testUpdateBasePrice() {
        priceCommandHandler.updateBasePrice(1000);

        verify(priceService, times(1)).updateBasePrice(1000);
    }

    @Test
    void testCreatePriceComponent() {
        priceCommandHandler.createPriceComponent("componentName", 1000);

        verify(priceService, times(1)).createPriceComponent("componentName", 1000);
    }

    @Test
    void testAttachPriceComponentToMovie() {
        priceCommandHandler.attachPriceComponentToMovie("componentName", "title");

        verify(priceService, times(1)).attachPriceComponentToMovie("componentName", "title");
    }

    @Test
    void attachPriceComponentToRoom() {
        priceCommandHandler.attachPriceComponentToRoom("componentName", "roomName");

        verify(priceService, times(1)).attachPriceComponentToRoom("componentName", "roomName");
    }

    @Test
    void testAttachPriceComponentToRoom() {
        priceCommandHandler.attachPriceComponentToScreening("componentName", "title", "roomName", "2030-01-01 08:10");

        verify(priceService, times(1)).attachPriceComponentToScreening(anyString(), anyString(), anyString(),
            any(LocalDateTime.class));
    }

//    @Test
//    void showPriceFor() {
//
//    }
}