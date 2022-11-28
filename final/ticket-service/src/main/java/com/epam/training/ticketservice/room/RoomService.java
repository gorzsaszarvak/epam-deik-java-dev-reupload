package com.epam.training.ticketservice.room;

import com.epam.training.ticketservice.room.persistence.Room;

import java.util.List;

public interface RoomService {

    List<Room> listRooms();

    void createRoom(String name, int rows, int columns);

    void updateRoom(String name, int rows, int columns);

    void deleteRoom(String name);

    Room findRoomByName(String name);

}
