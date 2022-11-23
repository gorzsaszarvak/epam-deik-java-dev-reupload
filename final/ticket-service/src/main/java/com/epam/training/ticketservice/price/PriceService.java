package com.epam.training.ticketservice.price;

import com.epam.training.ticketservice.screening.persistence.Screening;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceService {

    private int basePrice = 1500;

    public int getPrice(Screening screening, int numberOfSeats) {
        return numberOfSeats * basePrice;
    }
}
