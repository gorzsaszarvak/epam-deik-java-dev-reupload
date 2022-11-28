//package com.epam.training.ticketservice.room.initializer;
//
//import com.epam.training.ticketservice.room.persistence.Room;
//import com.epam.training.ticketservice.room.persistence.RoomRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class RoomInitializer {
//
//    private final RoomConfigurations roomConfigurations;
//    private final RoomRepository roomRepository;
//
//    @PostConstruct
//    public void initRooms() {
//        log.info("Initializing rooms...");
//        final List<Room> rooms = List.of(
//            new Room(roomConfigurations.getRoomName(),
//                roomConfigurations.getRoomRows(),
//                roomConfigurations.getRoomColumns())
//        );
//        roomRepository.saveAll(rooms);
//        roomRepository.findAll().forEach(System.out::println);
//        log.info("Rooms initialized.");
//    }
//}
