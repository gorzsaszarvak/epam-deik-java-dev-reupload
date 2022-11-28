package com.epam.training.ticketservice.room.impl;

import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.room.exception.NoRoomsFoundException;
import com.epam.training.ticketservice.room.exception.RoomAlreadyExistsException;
import com.epam.training.ticketservice.room.exception.RoomNotFoundException;
import com.epam.training.ticketservice.room.persistence.Room;
import com.epam.training.ticketservice.room.persistence.RoomRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> listRooms() throws NoRoomsFoundException {
        List<Room> rooms = roomRepository.findAll();
        if (!rooms.isEmpty()) {
            return rooms;
        } else {
            throw new NoRoomsFoundException();
        }
    }

    @Override
    public void createRoom(String name, int rows, int columns) throws RoomAlreadyExistsException {
        if (!roomRepository.existsByName(name)) {
            roomRepository.save(new Room(name, rows, columns));
        } else {
            throw new RoomAlreadyExistsException(name);
        }

    }

    @Override
    public void updateRoom(String name, int rows, int columns) throws RoomNotFoundException {
        roomRepository.delete(findRoomByName(name));
        roomRepository.save(new Room(name, rows, columns));


    }

    @Override
    public void deleteRoom(String name) throws RoomNotFoundException {
        roomRepository.delete(findRoomByName(name));
    }

    @Override
    public Room findRoomByName(String name) throws RoomNotFoundException {
        Optional<Room> room = roomRepository.findRoomByName(name);
        if (room.isPresent()) {
            return room.get();
        } else {
            throw new RoomNotFoundException(name);
        }
    }
}
