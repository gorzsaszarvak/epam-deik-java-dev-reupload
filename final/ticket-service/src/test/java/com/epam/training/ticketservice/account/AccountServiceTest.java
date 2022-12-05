package com.epam.training.ticketservice.account;

import com.epam.training.ticketservice.account.exception.AccountDoesntExistException;
import com.epam.training.ticketservice.account.exception.NotLoggedInException;
import com.epam.training.ticketservice.account.exception.UsernameAlreadyExistsException;
import com.epam.training.ticketservice.account.impl.AccountServiceImpl;
import com.epam.training.ticketservice.account.persistence.Account;
import com.epam.training.ticketservice.account.persistence.AccountRepository;
import com.epam.training.ticketservice.account.persistence.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    AccountServiceImpl accountService;

    @Mock
    AccountRepository accountRepository;

    private Account testAccount;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testCreateAccount() {
        accountService.createAccount("testAccount", "testAccount");

        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void testCreateAccountThrowsExceptionIfAccountAlreadyExists() {
        setTestAccountUser();
        when(accountRepository.findAccountByUsername(anyString())).thenReturn(Optional.of(testAccount));

        assertThrows(UsernameAlreadyExistsException.class,
            () -> accountService.createAccount("testAccount", "testAccount"));
        verify(accountRepository, times(0)).save(any(Account.class));
    }

    @Test
    void testFindAccountByUsername() {
        Account testAccount = Account.builder()
            .username("testAccount")
            .password("testAccount")
            .role(Role.ADMIN)
            .build();
        when(accountRepository.findAccountByUsername(anyString())).thenReturn(Optional.of(testAccount));

        accountService.findAccountByUsername(testAccount.getUsername());

        verify(accountRepository, times(1)).findAccountByUsername(testAccount.getUsername());
    }

    @Test
    void testFindAccountByUsernameThrowsExceptionIfAccountDoesntExist() {
        when(accountRepository.findAccountByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(AccountDoesntExistException.class, () -> accountService.findAccountByUsername("testAccount"));
    }

    @Test
    void testDescribeAccountWhenLoggedInAsAdmin() {
        setTestAccountAdmin();
        String expected = "Signed in with privileged account";
        when(accountRepository.findAccountByUsername(anyString())).thenReturn(Optional.of(testAccount));

        var actual = accountService.describeAccount();

        assertTrue(actual.contains(expected));
    }

    @Test
    void testDescribeAccountWhenLoggedInAsUser() {
        setTestAccountUser();
        String expected = "Signed in with account";
        when(accountRepository.findAccountByUsername(anyString())).thenReturn(Optional.of(testAccount));

        var actual = accountService.describeAccount();

        assertTrue(actual.contains(expected));
    }

    @Test
    void testDescribeAccountWhenNotLoggedIn() {
        assertThrows(NotLoggedInException.class, () ->
            accountService.describeAccount());
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