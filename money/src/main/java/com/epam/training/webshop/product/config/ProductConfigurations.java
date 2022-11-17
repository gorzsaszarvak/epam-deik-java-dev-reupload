package com.epam.training.webshop.product.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Component
@ConfigurationProperties(prefix = "products")
@Data
@Profile("! prod")
@Validated
public class ProductConfigurations {

    @NotBlank
    private String appleName;

    @Min(1)
    private double applePrice;

    @NotBlank
    private String applePackaging;

    @NotBlank
    private String melonName;

    @Min(1)
    private double melonPrice;

    @NotBlank
    private String melonPackaging;

    @NotBlank
    private String caviarName;

    @Min(1)
    private double caviarPrice;

    @NotBlank
    private String caviarPackaging;
}
