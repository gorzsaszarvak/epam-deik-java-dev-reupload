package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.booking.persistence.Seat;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.Availability;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class HelperMethods {

    public Availability loggedInAsAdmin() {
        if (signedIn()) {
            if (isAdmin()) {
                return Availability.available();
            } else {
                return Availability.unavailable("You do not have admin privileges");
            }
        } else {
            return Availability.unavailable("You are not signed in");
        }
    }

    public Availability loggedInAsUser() {
        Optional<Authentication> authentication =
            Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
        if (signedIn() && !isAdmin()) {
            return Availability.available();
        } else {
            return Availability.unavailable("You need to be logged into a user account");
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
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
            .anyMatch(x -> x.getAuthority().equals("ROLE_ADMIN"));
    }

    protected Date parseStartTime(String startTime) {
        try {
            Date date = new SimpleDateFormat("yyyy-mm-dd hh:mm").parse(startTime);
            return date;
        } catch (Exception exception) {
            throw new RuntimeException("Invalid date format");
        }
    }

    protected List<Seat> parseSeats(String seats) {
        return Arrays.stream(seats.split(" "))
            .map(x -> new Seat(Integer.parseInt(x.split(",")[0]), Integer.parseInt(x.split(",")[1])))
            .collect(Collectors.toList());
    }
}
