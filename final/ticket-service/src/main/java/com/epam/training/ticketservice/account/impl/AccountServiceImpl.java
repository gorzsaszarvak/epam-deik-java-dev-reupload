package com.epam.training.ticketservice.account.impl;

import com.epam.training.ticketservice.account.AccountService;
import com.epam.training.ticketservice.account.exception.AccountDoesntExistException;
import com.epam.training.ticketservice.account.exception.NotLoggedInException;
import com.epam.training.ticketservice.account.exception.UsernameAlreadyExistsException;
import com.epam.training.ticketservice.account.persistence.Account;
import com.epam.training.ticketservice.account.persistence.AccountRepository;
import com.epam.training.ticketservice.account.persistence.Role;
import com.epam.training.ticketservice.booking.persistence.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
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

            List<Booking> bookings = findAccountByUsername(user).getBookings();

            StringBuilder sb = new StringBuilder();
            sb.append("Signed in with");

            if (loggedInAsAdmin()) {

                sb.append(String.format(" privileged account '%s'", user));
                return sb.toString();
            } else if (!loggedInAsAdmin()) {

                sb.append(String.format(" account '%s'\n", user));
                if (bookings.isEmpty()) {
                    sb.append("You have not booked any tickets yet");
                } else {
                    sb.append("Your previous bookings are\n");
                    bookings.forEach(x -> sb.append(x.toString()).append("\n"));
                    sb.setLength(sb.length() - 1);
                }
                return sb.toString();
            }
        }
        throw new NotLoggedInException();
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

    private Boolean loggedInAsAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
            .anyMatch(x -> x.getAuthority().equals("ROLE_ADMIN"));
    }


}
