package com.bnppf.books.service;

import com.bnppf.books.web.exceptions.InvalidRequestException;
import com.bnppf.books.web.support.BasketDTO;
import com.bnppf.books.web.support.BasketItemDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    private static final double BOOK_PRICE = 50.0;
    private static final double[] DISCOUNTS = {0, 1.0, 0.95, 0.9, 0.8, 0.75};

    public double placeOrder(BasketDTO basket) {
        List<BasketItemDTO> items = basket.items();
        if (items.isEmpty()) {
            throw new InvalidRequestException("Basket cannot be empty.");
        }
        if (items.size() != items.stream().map(BasketItemDTO::bookId).distinct().count()) {
            throw new InvalidRequestException("Duplicate books in the basket.");
        }
        if (items.stream().map(BasketItemDTO::quantity).anyMatch(q -> q < 0)) {
            throw new InvalidRequestException("Book quantity cannot be negative.");
        }
        return findBestPrice(items);
    }

    public double findBestPrice(List<BasketItemDTO> items) {
        if (items.isEmpty()) {
            return 0;
        }
        double minPrice = Double.MAX_VALUE;

        // all possible combinations of book sets + avoid recalculation
        Set<List<BasketItemDTO>> seenSet = new HashSet<>();

        for (int setSize = 1; setSize <= items.size(); setSize++) {
            List<BasketItemDTO> remainingItems = new ArrayList<>(items);

            // Remove one book from each different book available up to setSize
            for (int i = 0; i < setSize; i++) {
                if (remainingItems.size() > i && remainingItems.get(i).quantity() > 0) {
                    BasketItemDTO updatedItem = new BasketItemDTO(
                            remainingItems.get(i).bookId(),
                            remainingItems.get(i).quantity() - 1
                    );
                    remainingItems.set(i, updatedItem);
                }
            }
            // Remove items with zero quantity
            remainingItems.removeIf(item -> item.quantity() == 0);

            if (!seenSet.contains(remainingItems)) {
                seenSet.add(remainingItems);
                double setPrice = setSize * BOOK_PRICE * DISCOUNTS[setSize];
                double remainingPrice = findBestPrice(remainingItems);
                minPrice = Math.min(minPrice, setPrice + remainingPrice);
            }

        }
        return minPrice;
    }
}
