package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.room.persistence.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class RoomCommandHandler extends HelperMethods {

    private final RoomService roomService;

    @ShellMethod(value = "Create room", key = "create room")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String createRoom(final String name, final int rows, final int columns) {
        try {
            roomService.createRoom(name, rows, columns);
            return "Room created";
        } catch (Exception exception) {
            return "Could not create room: " + exception.getMessage();
        }
    }

    @ShellMethod(value = "Update room", key = "update room")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String updateRoom(final String name, final int rows, final int columns) {
        try {
            roomService.updateRoom(name, rows, columns);
            return "Room updated";
        } catch (Exception exception) {
            return "Could not update room: " + exception.getMessage();
        }
    }

    @ShellMethod(value = "Delete room", key = "delete room")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String deleteRoom(final String name) {
        try {
            roomService.deleteRoom(name);
            return "Room deleted";
        } catch (Exception exception) {
            return "Could not delete room: " + exception.getMessage();
        }
    }

    @ShellMethod(value = "List rooms", key = "list rooms")
    public String listRooms() {
        try {
            roomService.listRooms().stream()
                .map(Room::toString)
                .forEach(System.out::println);
            return null;
        } catch (Exception exception) {
            return "There are no rooms at the moment";
        }
    }
}
