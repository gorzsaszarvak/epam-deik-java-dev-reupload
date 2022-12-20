package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.room.exception.NoRoomsFoundException;
import com.epam.training.ticketservice.room.exception.RoomAlreadyExistsException;
import com.epam.training.ticketservice.room.exception.RoomNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoomCommandHandlerTest {

    @InjectMocks
    RoomCommandHandler roomCommandHandler;

    @Mock
    RoomService roomService;

    private final String name = "roomName";
    private final int rows = 10;
    private final int columns = 10;


    @Test
    void testCreateRoom() {
        roomCommandHandler.createRoom(name, rows, columns);

        verify(roomService, times(1)).createRoom(name, rows, columns);
    }

    @Test
    void testCreateRoomHandlesExceptionIfRoomAlreadyExists() {
        doThrow(RoomAlreadyExistsException.class).when(roomService).createRoom(anyString(), anyInt(), anyInt());
        String expected = "Could not create room";

        var actual = roomCommandHandler.createRoom(name, rows, columns);

        assertTrue(actual.contains(expected));

    }

    @Test
    void testUpdateRoom() {
        roomCommandHandler.updateRoom(name, rows, columns);

        verify(roomService, times(1)).updateRoom(name, rows, columns);
    }

    @Test
    void testUpdateRoomHandlesExceptionIfRoomDoesntExist() {
        doThrow(RoomNotFoundException.class).when(roomService).updateRoom(anyString(), anyInt(), anyInt());
        String expected = "Could not update room";

        var actual = roomCommandHandler.updateRoom(name, rows, columns);

        assertTrue(actual.contains(expected));
    }

    @Test
    void testDeleteRoom() {
        roomCommandHandler.deleteRoom(name);

        verify(roomService, times(1)).deleteRoom(name);
    }

    @Test
    void testDeleteRoomHandlesExceptionIfRoomDoesntExist() {
        doThrow(RoomNotFoundException.class).when(roomService).deleteRoom(anyString());
        String expected = "Could not delete room";

        var actual = roomCommandHandler.deleteRoom(name);

        assertTrue(actual.contains(expected));
    }

    @Test
    void testListRooms() {
        roomCommandHandler.listRooms();

        verify(roomService, times(1)).listRooms();
    }

    @Test
    void testListRoomsHandlesExceptionWhenNoRoomsExist() {
        when(roomService.listRooms()).thenThrow(NoRoomsFoundException.class);
        String expected = "There are no rooms at the moment";

        String actual = roomCommandHandler.listRooms();

        verify(roomService, times(1)).listRooms();
        assertEquals(expected, actual);
    }


}