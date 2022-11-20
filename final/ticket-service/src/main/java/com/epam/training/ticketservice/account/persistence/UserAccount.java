package com.epam.training.ticketservice.account.persistence;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccount extends Account {

    //TODO(tickets)

    public UserAccount(String username, String password) {
        super(username, password);
    }

    @Override
    public String toString() {
        return "Signed in with account '" + super.getUsername() + "'";
    }
}
