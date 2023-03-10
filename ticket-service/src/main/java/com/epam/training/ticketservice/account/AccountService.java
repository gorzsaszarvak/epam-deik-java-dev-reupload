package com.epam.training.ticketservice.account;

import com.epam.training.ticketservice.account.persistence.Account;

public interface AccountService {

    void createAccount(String username, String password);

    String describeAccount();

    Account findAccountByUsername(String username);


}
