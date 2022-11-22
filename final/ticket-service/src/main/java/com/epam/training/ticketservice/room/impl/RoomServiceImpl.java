package com.epam.training.ticketservice.room.impl;

import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.room.exception.NoRoomsFoundException;
import com.epam.training.ticketservice.room.exception.RoomAlreadyExistsException;
import com.epam.training.ticketservice.room.exception.RoomNotFoundException;
import com.epam.training.ticketservice.room.persistence.Room;
import com.epam.training.ticketservice.room.persistence.RoomRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<String> listRoomsAsString() {
        List<String> roomsAsString = Stream.of(roomRepository.findAll())
            .map(x -> x.toString())
            .collect(Collectors.toList());
        if (!roomsAsString.isEmpty()) {
            return roomsAsString;
        } else {
            throw new NoRoomsFoundException();
        }
    }

    @Override
    public void createRoom(String name, int rows, int columns) {
        if (!roomRepository.findRoomByName(name).isPresent()) {
            roomRepository.save(new Room(name, rows, columns));
        } else {
            throw new RoomAlreadyExistsException(name);
        }

    }

    @Override
    public void updateRoom(String name, int rows, int columns) {
        if (roomRepository.findRoomByName(name).isPresent()) {
            roomRepository.delete(roomRepository.findRoomByName(name).get());
            createRoom(name, rows, columns);
        } else {
            throw new RoomNotFoundException(name);
        }

    }

    @Override
    public void deleteRoom(String name) {
        if (roomRepository.findRoomByName(name).isPresent()) {
            roomRepository.delete(roomRepository.findRoomByName(name).get());
        } else {
            throw new RoomNotFoundException(name);
        }
    }
}
