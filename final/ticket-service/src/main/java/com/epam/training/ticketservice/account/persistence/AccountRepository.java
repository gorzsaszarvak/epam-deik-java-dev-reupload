package com.epam.training.ticketservice.account.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findAccountByUsername(String username);

    Optional<Account> findAccountByIsActive(boolean active);

}
