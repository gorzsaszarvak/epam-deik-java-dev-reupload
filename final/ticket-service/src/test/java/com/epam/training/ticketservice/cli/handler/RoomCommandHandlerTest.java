package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.room.RoomService;
import com.epam.training.ticketservice.room.exception.NoRoomsFoundException;
import com.epam.training.ticketservice.room.persistence.Room;
import com.epam.training.ticketservice.room.persistence.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
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

//    @Mock
//    RoomRepository roomRepository;

    private final String name = "roomName";
    private final int rows = 10;
    private final int columns = 10;


    //todo test exception handling

    @Test
    void testCreateRoom() {
        roomCommandHandler.createRoom(name, rows, columns);

        verify(roomService, times(1)).createRoom(name, rows, columns);
    }

//    @Test
//    void testCreateRoomHandlesExceptionIfRoomExists() {
//        roomCommandHandler.createRoom(name, rows, columns);
//
//        verify(roomRepository, times(0)).save(any(Room.class));
//    }

    @Test
    void testUpdateRoom() {
        roomCommandHandler.updateRoom(name, rows, columns);

        verify(roomService, times(1)).updateRoom(name, rows, columns);
    }

//    @Test
//    void testUpdateRoomHandlesExceptionIfRoomDoesntExist() {
//        roomCommandHandler.updateRoom(name, rows, columns);
//
//        verify(roomRepository, times(0)).save(any(Room.class));
//    }

    @Test
    void testDeleteRoom() {
        roomCommandHandler.deleteRoom(name);

        verify(roomService, times(1)).deleteRoom(name);
    }

//    @Test
//    void testDeleteRoomHandlesExceptionIfRoomDoesntExist() {
//        roomCommandHandler.deleteRoom(name);
//
//        verify(roomRepository, times(0)).delete(any(Room.class));
//    }

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