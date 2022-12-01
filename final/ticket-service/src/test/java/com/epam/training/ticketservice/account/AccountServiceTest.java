package com.epam.training.ticketservice.account;

import com.epam.training.ticketservice.account.exception.AccountDoesntExistException;
import com.epam.training.ticketservice.account.exception.UsernameAlreadyExistsException;
import com.epam.training.ticketservice.account.impl.AccountServiceImpl;
import com.epam.training.ticketservice.account.persistence.Account;
import com.epam.training.ticketservice.account.persistence.AccountRepository;
import com.epam.training.ticketservice.account.persistence.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;


//todo more tests
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @InjectMocks
    AccountServiceImpl accountService;

    @Mock
    AccountRepository accountRepository;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        testAccount = Account.builder()
            .username("testAccount")
            .password("testAccount")
            .role(Role.USER)
            .build();
    }

    @Test
    void testCreateAccount() {
        accountService.createAccount("testAccount", "testAccount");

        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void testCreateAccountThrowsExceptionIfAccountAlreadyExists() {
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
}