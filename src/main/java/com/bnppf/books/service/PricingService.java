package com.bnppf.books.service;

public class PricingService {

    private static final double BOOK_PRICE = 50.0;
    private static final double[] DISCOUNTS = {1.0, 0.95, 0.9, 0.8, 0.75};

    public double calculatePrice(int[] basket) {
        double totalPrice = 0;
        for (int basketItem : basket) {
            totalPrice = basketItem * BOOK_PRICE;
        }
        return totalPrice;
    }
}
