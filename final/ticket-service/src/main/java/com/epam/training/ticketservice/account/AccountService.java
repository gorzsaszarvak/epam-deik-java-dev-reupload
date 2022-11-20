package com.epam.training.ticketservice.account;

import java.util.List;

public interface AccountService {

//    void createAdminAccount(String username, String password);

    void createUserAccount(String username, String password);

    void signIn(String username, String password);

    String describeAccount();

    Boolean loggedInAsAdmin();
}
