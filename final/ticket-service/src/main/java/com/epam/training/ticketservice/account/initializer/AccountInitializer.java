package com.epam.training.ticketservice.account.initializer;

import com.epam.training.ticketservice.account.config.AccountConfigurations;
import com.epam.training.ticketservice.account.persistence.Account;
import com.epam.training.ticketservice.account.persistence.AccountRepository;
import com.epam.training.ticketservice.account.persistence.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountInitializer {
    private final AccountConfigurations accountConfigurations;
    private final AccountRepository accountRepository;

    @PostConstruct
    public void initAccounts() {
        log.info("Initializing accounts...");
        Account admin = Account.builder()
                        .username(accountConfigurations.getAdminUsername())
                        .password(accountConfigurations.getAdminPassword())
                        .role(Role.ADMIN)
                        .build();
        accountRepository.save(admin);
        log.info("Accounts initialized.");
    }
}
