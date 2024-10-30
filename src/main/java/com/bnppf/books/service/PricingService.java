package com.bnppf.books.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PricingService {

    private static final double BOOK_PRICE = 50.0;
    private static final double[] DISCOUNTS = {0, 1.0, 0.95, 0.9, 0.8, 0.75};

    public double calculatePrice(int[] basket) {
        if (basket.length == 0 || Arrays.stream(basket).sum() == 0) {
            return 0;
        }

        double totalPrice = 0;

        List<Integer> quantities = new ArrayList<>();
        for (int basketItem : basket) {
            if (basketItem > 0) {
                quantities.add(basketItem);
            }
        }

        // try to get throw all available books - greedy way
        while (!quantities.isEmpty()) {
            // Calculate the price based on the current size of quantities
            int currentSize = quantities.size();
            double currentTotal = currentSize * BOOK_PRICE * DISCOUNTS[currentSize];
            totalPrice += currentTotal;

            quantities.replaceAll(quantity -> quantity - 1);

            // remove empty quantities
            quantities.removeIf(q -> q == 0);
        }

        return totalPrice;
    }
}
