package com.epam.training.ticketservice.account.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
public class AdminAccount extends Account {

    public AdminAccount(String username, String password) {
        super(username, password);
    }

    @Override
    public String toString() {
        return "Signed in with privileged account '" + super.getUsername() + "'";
    }

}