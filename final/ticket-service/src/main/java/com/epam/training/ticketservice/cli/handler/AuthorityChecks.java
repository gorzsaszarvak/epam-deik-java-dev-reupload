package com.epam.training.ticketservice.cli.handler;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.Availability;

import java.util.Optional;

public abstract class AuthorityChecks {

    public Availability loggedInAsAdmin() {
        if (signedIn()) {
            if (isAdmin()) {
                return Availability.available();
            } else {
                return Availability.unavailable("You do not have admin privileges.");
            }
        } else {
            return Availability.unavailable("You are not signed in.");
        }
    }

    public Availability notLoggedIn() {
        Optional<Authentication> authentication =
            Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
        if (signedIn()) {
            return Availability.unavailable(String.format("Already logged in as '%n'", authentication.get().getName()));
        } else {
            return Availability.available();
        }
    }

    private boolean signedIn() {
        Optional<Authentication> authentication =
            Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
        return authentication.isPresent() && authentication.get() instanceof UsernamePasswordAuthenticationToken;
    }

    private boolean isAdmin() {
        return SecurityContextHolder.getContext()
            .getAuthentication()
            .getAuthorities()
            .stream()
            .anyMatch(x -> x.getAuthority()
                .equals("ROLE_ADMIN"));
    }
}
