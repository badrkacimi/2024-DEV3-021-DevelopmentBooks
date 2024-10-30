package com.bnppf.books.service;

import com.bnppf.books.web.support.BasketDTO;
import com.bnppf.books.web.support.BasketItemDTO;

import java.util.List;

public class OrderService {

    private static final double BOOK_PRICE = 50.0;
    private static final double[] DISCOUNTS = {0, 1.0, 0.95, 0.9, 0.8, 0.75};

    public double placeOrder(BasketDTO basket) {
        List<BasketItemDTO> items = basket.items();

        if (items.isEmpty()) {
            return 0;
        }

        double totalPrice = 0;

        // try to get throw all available books - greedy way
        while (!items.isEmpty()) {
            // Calculate the price based on the current size of quantities
            int currentSize = items.size();
            double currentTotal = currentSize * BOOK_PRICE * DISCOUNTS[currentSize];
            totalPrice += currentTotal;

            items = items.stream()
                    .map(item -> new BasketItemDTO(item.bookId(), item.quantity() - 1))
                    .filter(item -> item.quantity() > 0) // Keep only items with quantity > 0
                    .toList();
        }

        return totalPrice;
    }
}
