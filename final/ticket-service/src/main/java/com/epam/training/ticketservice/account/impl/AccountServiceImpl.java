package com.epam.training.ticketservice.account.impl;

import com.epam.training.ticketservice.account.AccountService;
import com.epam.training.ticketservice.account.exception.*;
import com.epam.training.ticketservice.account.persistence.Account;
import com.epam.training.ticketservice.account.persistence.AccountRepository;
import com.epam.training.ticketservice.account.persistence.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;


    @Override
    public void createAccount(String username, String password) {
        if(accountRepository.findAccountByUsername(username).isEmpty()){
            accountRepository.save(
                    Account.builder()
                        .username(username)
                        .password(password)
                        .role(Role.USER)
                        .build());
        } else {
            throw new UsernameAlreadyExistsException(username);
        }
    }

    @Override
    public String describeAccount() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(!username.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Signed in with ");

            if(loggedInAsAdmin()){
                sb.append(String.format("privileged account '%s'" , username));
            } else {
                sb.append(String.format("account '%s'", username));
                //TODO(bookings)
                sb.append("\n You have not booked any tickets yet");
            }

            return sb.toString();
        } else {
            throw new NotLoggedInException();
        }
    }

    @Override
    public Boolean loggedInAsAdmin() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(x -> x.getAuthority().equals("ROLE_ADMIN"));
    }

    @Override
    public Account findAccountByUsername(String username) {
        var account = accountRepository.findAccountByUsername(username);
        if(account.isPresent()) {
            return account.get();
        } else {
            throw new AccountDoesntExistException(username);
        }
    }


}
