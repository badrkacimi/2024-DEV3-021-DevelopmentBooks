package com.bnppf.books.service;

public class PricingService {

    private static final double BOOK_PRICE = 50.0;
    private static final double[] DISCOUNTS = {0, 1.0, 0.95, 0.9, 0.8, 0.75};

    public double calculatePrice(int[] basket) {
        double totalPrice = 0;
        double discountedPrice;
        int size = basket.length;

        for (int basketItem : basket) {
            totalPrice += basketItem * BOOK_PRICE;
        }

        discountedPrice = totalPrice * DISCOUNTS[size];

        return discountedPrice;
    }
}
