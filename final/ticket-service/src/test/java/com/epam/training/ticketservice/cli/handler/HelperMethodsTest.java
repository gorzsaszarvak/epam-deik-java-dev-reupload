package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.account.persistence.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class HelperMethodsTest {

    HelperMethods helperMethods;

    private Account testAccount;


    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testLoggedInAsAdminIsAdmin() {
//        setTestAccountAdmin();
//        Availability expected = Availability.available();
//
//        var actual = helperMethods.loggedInAsAdmin();
//
//        assertEquals(expected, actual);
    }

    @Test
    void loggedInAsUser() {
    }

    @Test
    void notLoggedIn() {
    }

    @Test
    void parseStartTime() {
    }

    @Test
    void parseSeats() {
    }

    private void setTestAccountAdmin() {
        testAccount = Account.builder().username("admin").password("admin").bookings(List.of()).build();
        Authentication authentication = new TestingAuthenticationToken(testAccount.getUsername(), testAccount,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    private void setTestAccountUser() {
        testAccount = Account.builder().username("user").password("user").bookings(List.of()).build();
        Authentication authentication = new TestingAuthenticationToken(testAccount.getUsername(), testAccount,
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

}