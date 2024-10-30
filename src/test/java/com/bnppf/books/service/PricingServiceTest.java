package com.bnppf.books.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PricingServiceTest {

    @InjectMocks
    PricingService pricingService;

    @Test
    public void calculatePrice_SingleBook_NoDiscount() {
        double price = pricingService.calculatePrice(new int[]{1});
        assertEquals(50.0, price, "Price for a single book should be 50.0");
    }

    @Test
    public void calculatePrice_TwoSameBooks_NoDiscount() {
        double price = pricingService.calculatePrice(new int[]{2});
        assertEquals(100.0, price, "Price for a two same book should be 100.0");

    }

    @Test
    void calculatePrice_TwoDifferentBooks_AppliesDiscount() {
        double price = pricingService.calculatePrice(new int[]{1, 1});
        assertEquals(95, price);
    }

    @Test
    void calculatePrice_FiveDifferentBooks_AppliesDiscount() {
        double price = pricingService.calculatePrice(new int[]{1, 1, 1, 1, 1});
        assertEquals(187.5, price);
    }
}