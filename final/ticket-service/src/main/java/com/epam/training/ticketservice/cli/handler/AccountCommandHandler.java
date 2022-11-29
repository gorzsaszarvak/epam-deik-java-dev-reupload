package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class AccountCommandHandler extends HelperMethods {

    private final AccountService accountService;

    private final AuthenticationManager authenticationManager;

    @ShellMethod(value = "sign in privileged 'username' 'password'", key = "sign in privileged")
    @ShellMethodAvailability(value = "notLoggedIn")
    public String signInPrivileged(String username, String password) {
        try {
            Authentication request = new UsernamePasswordAuthenticationToken(username, password);
            SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(request));
            return String.format("Signed in with privileged account '%s'", username);
        } catch (Exception exception) {
            return "Login failed due to incorrect credentials";
        }
    }

    @ShellMethod(value = "sign in 'username' 'password'", key = "sign in")
    @ShellMethodAvailability(value = "notLoggedIn")
    public String signIn(String username, String password) {
        try {
            Authentication request = new UsernamePasswordAuthenticationToken(username, password);
            SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(request));
            return "Signed in as '" + username + "'";
        } catch (Exception exception) {
            return "Login failed due to incorrect credentials";
        }
    }

    @ShellMethod(value = "sign out", key = "sign out")
    public void signOut() {
        try {
            SecurityContextHolder.getContext().setAuthentication(null);
            System.out.println("Signed out");
        } catch (AuthenticationException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @ShellMethod(value = "sign up 'username' 'password'", key = "sign up")
    public String signUp(String username, String password) {
        try {
            accountService.createAccount(username, password);
            return "Created account with username '" + username + "'";
        } catch (Exception exception) {
            return "Error signing up: " + exception.getMessage();
        }
    }

    @ShellMethod(value = "describe account", key = "describe account")
    public String describeAccount() {
        try {
            return accountService.describeAccount();
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

}
