package com.epam.training.ticketservice.account.impl;

import com.epam.training.ticketservice.account.AccountService;
import com.epam.training.ticketservice.account.exception.AccountDoesntExistException;
import com.epam.training.ticketservice.account.exception.NotLoggedInException;
import com.epam.training.ticketservice.account.exception.UsernameAlreadyExistsException;
import com.epam.training.ticketservice.account.persistence.Account;
import com.epam.training.ticketservice.account.persistence.AccountRepository;
import com.epam.training.ticketservice.account.persistence.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;


    @Override
    public void createAccount(String username, String password) {
        if (accountRepository.findAccountByUsername(username).isEmpty()) {
            accountRepository.save(Account.builder().username(username).password(password).role(Role.USER).build());
        } else {
            throw new UsernameAlreadyExistsException(username);
        }
    }

    public String describeAccount() {

        if (SecurityContextHolder.getContext().getAuthentication() != null) {

            String user = SecurityContextHolder.getContext().getAuthentication().getName();

            StringBuilder sb = new StringBuilder();
            sb.append("Signed in with");

            if (loggedInAsAdmin()) {

                sb.append(String.format(" privileged account '%s'", user));

                return sb.toString();
            } else if (!loggedInAsAdmin()) {
                sb.append(String.format(" account '%s'\n", user));

                sb.append("You have not booked any tickets yet");

                return sb.toString();

            }
        }
        throw new NotLoggedInException();
    }

    @Override
    public Boolean loggedInAsAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
            .anyMatch(x -> x.getAuthority().equals("ROLE_ADMIN"));
    }

    @Override
    public Account findAccountByUsername(String username) {
        Optional<Account> account = accountRepository.findAccountByUsername(username);
        if (account.isPresent()) {
            return account.get();
        } else {
            throw new AccountDoesntExistException(username);
        }
    }


}
