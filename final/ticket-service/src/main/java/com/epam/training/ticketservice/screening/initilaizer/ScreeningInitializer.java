//package com.epam.training.ticketservice.screening.initilaizer;
//
//import com.epam.training.ticketservice.cli.handler.HelperMethods;
//import com.epam.training.ticketservice.screening.ScreeningService;
//import com.epam.training.ticketservice.screening.persistence.Screening;
//import com.epam.training.ticketservice.screening.persistence.ScreeningRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class ScreeningInitializer extends HelperMethods {
//
//    private final ScreeningConfigurations screeningConfigurations;
//    private final ScreeningService screeningService;
//
//    @PostConstruct
//    public void initMovies() {
//        log.info("Initializing screenings...");
//        screeningService.createScreening(
//            screeningConfigurations.getMovieTitle(),
//            screeningConfigurations.getRoomName(),
//            parseStartTime(screeningConfigurations.getStartTime()));
//        log.info("Screenings initialized.");
//    }
//
//}
