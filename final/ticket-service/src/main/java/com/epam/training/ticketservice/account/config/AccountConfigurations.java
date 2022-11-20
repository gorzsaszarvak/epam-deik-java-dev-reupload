package com.epam.training.ticketservice.account.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Component
@ConfigurationProperties(prefix = "accounts")
@Data
@Validated
public class AccountConfigurations {

    @NotBlank
    private String adminUsername;

    @NotBlank String adminPassword;
}
