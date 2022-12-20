package com.epam.training.ticketservice.account;

import com.epam.training.ticketservice.account.exception.AccountDoesntExistException;
import com.epam.training.ticketservice.account.persistence.Account;
import com.epam.training.ticketservice.account.persistence.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountDetailsServiceTest {

    @InjectMocks
    AccountDetailsService accountDetailsService;

    @Mock
    AccountService accountService;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        testAccount = Account.builder().username("testAccount").password("testAccount").build();
    }

    @Test
    void testLoadUserByUsernameReturnsAdminRoleForAdmin() {
        testAccount.setRole(Role.ADMIN);
        when(accountService.findAccountByUsername(testAccount.getUsername())).thenReturn(testAccount);

        UserDetails userDetails = accountDetailsService.loadUserByUsername(testAccount.getUsername());

        assertTrue(userDetails.getAuthorities().stream().anyMatch(x -> x.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void testLoadUserByUsernameReturnsUserRoleForUser() {
        testAccount.setRole(Role.USER);
        when(accountService.findAccountByUsername(testAccount.getUsername())).thenReturn(testAccount);

        UserDetails userDetails = accountDetailsService.loadUserByUsername(testAccount.getUsername());

        Assertions.assertFalse(
            userDetails.getAuthorities().stream().anyMatch(x -> x.getAuthority().equals("ROLE_ADMIN")));
        Assertions.assertTrue(
            userDetails.getAuthorities().stream().anyMatch(x -> x.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void testLoadUserByUsernameThrowsExceptionWhenAccountDoesntExist() {
        when(accountService.findAccountByUsername(testAccount.getUsername())).thenThrow(
            AccountDoesntExistException.class);

        assertThrows(UsernameNotFoundException.class,
            () -> accountDetailsService.loadUserByUsername(testAccount.getUsername()));
    }
}