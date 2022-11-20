package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.account.AccountService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class AccountCommandHandler {
    private final AccountService accountService;

    public AccountCommandHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @ShellMethod(value = "Sign into privileged account", key = "sign in privileged")
    public String signInPrivileged(String username, String password) {
        try {
            accountService.signIn(username, password);
            return "Signed in as '" + username + "' with admin privileges";
        } catch (Exception exception) {
            return "Error signing in: " + exception.getMessage();
        }
    }

    @ShellMethod(value = "Sign into account", key = "sign in")
    public String signIn(String username, String password) {
        try {
            accountService.signIn(username, password);
            return "Signed in as '" + username + "'";
        } catch (Exception exception) {
            return "Error signing in: " + exception.getMessage();
        }
    }

    @ShellMethod(value = "Create new user account", key = "sign up")
    public String signUp(String username, String password){
        try {
            accountService.createUserAccount(username, password);
            return "Created account with username '" + username + "'";
        } catch (Exception exception) {
        return "Error signing in: " + exception.getMessage();
        }
    }

    @ShellMethod(value = "Describe the account currently signed into", key = "describe account")
    public String describeAccount(){
        try {
            return accountService.describeAccount();
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

}
