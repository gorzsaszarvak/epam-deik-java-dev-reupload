package com.epam.training.ticketservice.account.initializer;

import com.epam.training.ticketservice.account.config.AccountConfigurations;
import com.epam.training.ticketservice.account.persistence.AccountRepository;
import com.epam.training.ticketservice.account.persistence.AdminAccount;
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
        accountRepository.save(new AdminAccount(
                accountConfigurations.getAdminUsername(),
                accountConfigurations.getAdminPassword()));
        log.info("Accounts initialized.");
    }
}
