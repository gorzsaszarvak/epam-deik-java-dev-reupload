package com.epam.training.ticketservice.room;

import java.util.List;

public interface RoomService {

    List<String> listRoomsAsString();

    void createRoom(String name, int rows, int columns);

    void updateRoom(String name, int rows, int columns);

    void deleteRoom(String name);

}
