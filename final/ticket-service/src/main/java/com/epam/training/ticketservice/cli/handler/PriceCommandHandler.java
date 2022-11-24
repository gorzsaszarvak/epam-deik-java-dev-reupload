package com.epam.training.ticketservice.cli.handler;

import com.epam.training.ticketservice.price.impl.PriceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@RequiredArgsConstructor
public class PriceCommandHandler extends HelperMethods{

    private final PriceServiceImpl priceService;

    @ShellMethod(value = "update base price 'price'", key = "update base price")
    @ShellMethodAvailability(value = "loggedInAsAdmin")
    public String updateBasePrice(final int basePrice) {
        priceService.updateBasePrice(basePrice);
        return "Updated base price";
    }
}
