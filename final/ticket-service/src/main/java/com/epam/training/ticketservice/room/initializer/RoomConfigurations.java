package com.epam.training.ticketservice.room.initializer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Component
@ConfigurationProperties(prefix = "rooms")
@Data
@Validated
public class RoomConfigurations {

    @NotBlank
    private String roomName;

    @Min(1)
    private int roomRows;

    @Min(1)
    private int roomColumns;
}
