package com.epam.training.webshop.order.confirmation.lib.impl;

import com.epam.training.webshop.order.confirmation.lib.ConfirmationService;
import com.epam.training.webshop.product.persistence.entitiy.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EmailConfirmationService implements ConfirmationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailConfirmationService.class);

    @Override
    public void sendConfirmationMessageAbout(List<Product> products) {
        LOGGER.info("Sending an e-mail confirmation about {} products", products);
    }
}
