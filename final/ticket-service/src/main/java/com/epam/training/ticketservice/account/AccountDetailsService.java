package com.epam.training.ticketservice.account;

import com.epam.training.ticketservice.account.persistence.Account;
import com.epam.training.ticketservice.account.persistence.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {
    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Account account = accountService.findAccountByUsername(username);

            User.UserBuilder builder = User.withUsername(username);
            builder.password(new BCryptPasswordEncoder().encode(account.getPassword()));

            if (account.getRole().equals(Role.ADMIN)) {
                builder.roles("USER", "ADMIN");
            } else if (account.getRole().equals(Role.USER)) {
                builder.roles("USER");
            }

            return builder.build();
        } catch (Exception exception) {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
