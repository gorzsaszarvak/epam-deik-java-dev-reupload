package com.epam.training.ticketservice.movie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieInitializer {

    private final MovieConfigurations movieConfigurations;
    private final MovieRepository movieRepository;

    @PostConstruct
    public void initMovies() {
        log.info("Initializing movies...");
        final List<Movie> movies = List.of(
                new Movie(movieConfigurations.getStarWarName(), movieConfigurations.getStarWarGenre(), movieConfigurations.getStarWarLength()),
                new Movie(movieConfigurations.getPulpFicName(), movieConfigurations.getPulpFicGenre(), movieConfigurations.getPulpFicLength())
        );
        movieRepository.saveAll(movies);
        movieRepository.findAll().forEach(System.out::println);
        log.info("Movies initialized.");

    }
}
