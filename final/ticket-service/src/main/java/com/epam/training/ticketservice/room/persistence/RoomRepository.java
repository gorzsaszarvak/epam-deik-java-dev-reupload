package com.epam.training.ticketservice.room.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    Optional<Room> findRoomByName(String name);
}
