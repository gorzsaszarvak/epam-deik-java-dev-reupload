 package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.room.RoomService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

 @ShellComponent
public class RoomCommandHandler {

    private final RoomService roomService;


    public RoomCommandHandler(RoomService roomService) {
        this.roomService = roomService;
    }

    @ShellMethod(value = "Create room", key = "create room")
    //TODO(admin method)
    public String createRoom(final String name, final int rows, final int columns) {
        try {
            roomService.createRoom(name, rows, columns);
            return "Created room with name: " + name;
        } catch (Exception exception) {
            return "Could not create room, reason: " + exception.getMessage();
        }
    }

    @ShellMethod(value = "Update room", key = "update room")
    //TODO(admin method)
    public String updateRoom(final String name, final int rows, final int columns) {
        try {
            roomService.updateRoom(name, rows, columns);
            return "Updated room with name: " + name;
        } catch (Exception exception) {
            return "Could not update room, reason:" + exception.getMessage();
        }
    }

    @ShellMethod(value = "Delete room", key = "delete room")
    //TODO(admin method)
    public String deleteRoom(final String name) {
        try {
            roomService.deleteRoom(name);
            return "Deleted room with name: " + name;
        } catch (Exception exception) {
            return "Could not delete movie, reason: " + exception.getMessage();
        }
    }

     //TODO(can't print in separate lines)
    @ShellMethod(value = "List rooms", key = "list rooms")
    public String listRooms() {
        try {
            List<String> roomsAsString = roomService.listRoomsAsString();

            StringBuilder stringBuilder = new StringBuilder();
            for(String room : roomsAsString) {
                stringBuilder.append(room).append(System.lineSeparator());
            }
            return  stringBuilder.toString();
        } catch (Exception exception) {
            return "There are no rooms at the moment";
        }
    }

}
