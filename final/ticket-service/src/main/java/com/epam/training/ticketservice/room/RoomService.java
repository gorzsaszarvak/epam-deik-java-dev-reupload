package com.epam.training.ticketservice.room;

import com.epam.training.ticketservice.room.persistence.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    List<String> listRoomsAsString();

    void createRoom(String name, int rows, int columns);

    void updateRoom(String name, int rows, int columns);

    void deleteRoom(String name);

    Optional<Room> findRoomByName(String name);

}
