package com.epam.training.ticketservice.account;

import java.util.List;

public interface AccountService {

//    void createAdminAccount(String username, String password);

    void createUserAccount(String username, String password);

    void signIn(String username, String password);
    //TODO(account is not set active for some reason)

    String describeAccount();

    Boolean loggedInAsAdmin();
}
