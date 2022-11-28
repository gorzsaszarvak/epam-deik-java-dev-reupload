package com.epam.training.ticketservice.room;

import com.epam.training.ticketservice.room.exception.NoRoomsFoundException;
import com.epam.training.ticketservice.room.exception.RoomAlreadyExistsException;
import com.epam.training.ticketservice.room.exception.RoomNotFoundException;
import com.epam.training.ticketservice.room.impl.RoomServiceImpl;
import com.epam.training.ticketservice.room.persistence.Room;
import com.epam.training.ticketservice.room.persistence.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @InjectMocks
    RoomServiceImpl roomService;

    @Mock
    private RoomRepository roomRepository;

    private Room testRoom;

    @BeforeEach
    void setUp() {
        testRoom = new Room("testName", 10, 10);
    }


    @Test
    void testListRooms() {
        List<Room> expected = List.of(testRoom);

        when(roomRepository.findAll()).thenReturn(expected);
        List<Room> actual = roomService.listRooms();

        assertEquals(expected, actual);
    }

    @Test
    void testListRoomsThrowsExceptionWhenNoRoomsFound() {
        when(roomRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NoRoomsFoundException.class, () -> roomService.listRooms());
    }

    @Test
    void testCreateRoom() {
        when(roomRepository.existsByName(anyString())).thenReturn(false);

        roomService.createRoom(testRoom.getName(), testRoom.getRows(), testRoom.getColumns());

        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void testCreateRoomThrowsExceptionWhenRoomAlreadyExists() {
        when(roomRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(RoomAlreadyExistsException.class,
            () -> roomService.createRoom(testRoom.getName(), testRoom.getRows(), testRoom.getColumns()));
        verify(roomRepository, times(0)).save(any(Room.class));
    }

    @Test
    void testUpdateRoom() {
        when(roomRepository.findRoomByName(anyString())).thenReturn(Optional.of(
            new Room(testRoom.getName(), testRoom.getRows(), testRoom.getColumns())));

        roomService.updateRoom(testRoom.getName(), testRoom.getRows(), testRoom.getColumns());

        verify(roomRepository, times(1)).delete(any(Room.class));
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void testUpdateRoomThrowsExceptionWhenRoomDoesntExist() {
        when(roomRepository.findRoomByName(anyString())).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class,
            () -> roomService.updateRoom(testRoom.getName(), testRoom.getRows(), testRoom.getColumns()));
        verify(roomRepository, times(0)).save(any(Room.class));
    }

    @Test
    void testDeleteRoom() {
        when(roomRepository.findRoomByName(anyString())).thenReturn(Optional.of(
            new Room(testRoom.getName(), testRoom.getRows(), testRoom.getColumns())));

        roomService.deleteRoom(testRoom.getName());

        verify(roomRepository, times(1)).delete(any(Room.class));
    }

    @Test
    void testDeleteRoomThrowsExceptionWhenRoomDoesntExist() {
        when(roomRepository.findRoomByName(anyString())).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class,
            () -> roomService.deleteRoom(testRoom.getName()));
        verify(roomRepository, times(0)).delete(any(Room.class));
    }


    @Test
    void testFindRoomByName() {
        Room expected = new Room(testRoom.getName(), testRoom.getRows(), testRoom.getColumns());
        when(roomRepository.findRoomByName(testRoom.getName())).thenReturn(Optional.of(expected));

        var actual = roomService.findRoomByName(testRoom.getName());

        assertEquals(expected, actual);
    }
}