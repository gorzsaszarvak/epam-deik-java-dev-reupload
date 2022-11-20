package com.epam.training.ticketservice.account.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Account{

    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String password;
    private boolean isActive;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setActiveTrue() {
        this.isActive = true;
    }

    public void setActiveFalse() {
        this.isActive = false;
    }
}
