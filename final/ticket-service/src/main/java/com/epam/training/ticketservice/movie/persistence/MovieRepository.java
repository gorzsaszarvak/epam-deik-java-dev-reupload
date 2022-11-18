package com.epam.training.ticketservice.movie.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    Optional<Movie> findMovieByTitle(String name);

}

