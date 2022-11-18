package com.epam.training.ticketservice.room.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Long> {

    Optional<Room> findRoomByName(String name);
}
