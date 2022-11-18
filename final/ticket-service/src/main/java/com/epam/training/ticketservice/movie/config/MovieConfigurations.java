package com.epam.training.ticketservice.movie.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Component
@ConfigurationProperties(prefix = "movies")
@Data
@Validated
public class MovieConfigurations {

    @NotBlank
    private String starWarName;

    @NotBlank
    private String starWarGenre;

    @Min(1)
    private int starWarLength;

    @NotBlank
    private String pulpFicName;

    @NotBlank
    private String pulpFicGenre;

    @Min(1)
    private int pulpFicLength;

}
