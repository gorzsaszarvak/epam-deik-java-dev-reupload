package com.epam.training.webshop.order.confirmation.lib;

import com.epam.training.webshop.product.persistence.entitiy.Product;

import java.util.List;

public interface ConfirmationService {

    void sendConfirmationMessageAbout(List<Product> products);
}
