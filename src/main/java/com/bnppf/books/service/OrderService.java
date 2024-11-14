package com.bnppf.books.service;

import com.bnppf.books.domain.repositories.BookRepository;
import com.bnppf.books.web.exceptions.InvalidRequestException;
import com.bnppf.books.web.support.BasketDTO;
import com.bnppf.books.web.support.BasketItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {
    private static final double BOOK_PRICE = 50.0;
    private static final double[] DISCOUNTS = {0, 1.0, 0.95, 0.9, 0.8, 0.75};
    private final BookRepository bookRepository;

    public double placeOrder(BasketDTO basket) {
        validateBasket(basket);
        checkBookAvailability(basket);
        return calculateBestPrice(basket.items());
    }

    private void validateBasket(BasketDTO basket) {
        List<BasketItemDTO> items = basket.items();

        if (items.isEmpty() || items.stream().mapToDouble(BasketItemDTO::quantity).sum() == 0) {
            throw new InvalidRequestException("Basket cannot be empty.");
        }
        if (items.size() != items.stream().map(BasketItemDTO::bookId).distinct().count()) {
            throw new InvalidRequestException("Duplicate books in the basket.");
        }
        if (items.stream().map(BasketItemDTO::quantity).anyMatch(q -> q < 0)) {
            throw new InvalidRequestException("Book quantity cannot be negative.");
        }
    }

    private void checkBookAvailability(BasketDTO basket) {
        for (BasketItemDTO basketItemDTO : basket.items()) {
            var book = bookRepository.findById(basketItemDTO.bookId());
            if (book.isEmpty()) {
                throw new InvalidRequestException("Book unavailable. Id: " + basketItemDTO.bookId());
            }
        }
    }

    private double calculateBestPrice(List<BasketItemDTO> items) {
        if (items.isEmpty()) {
            return 0;
        }
        double minPrice = Double.MAX_VALUE;
        Set<List<BasketItemDTO>> seenSet = new HashSet<>();

        for (int setSize = 1; setSize <= items.size(); setSize++) {
            List<BasketItemDTO> remainingItems = new ArrayList<>(items);
            createBookSet(remainingItems, setSize);

            remainingItems.removeIf(item -> item.quantity() == 0);
            if (!seenSet.contains(remainingItems)) {
                seenSet.add(remainingItems);
                double setPrice = setSize * BOOK_PRICE * DISCOUNTS[setSize];
                double remainingPrice = calculateBestPrice(remainingItems);
                minPrice = Math.min(minPrice, setPrice + remainingPrice);
            }
        }
        return minPrice;
    }

    private void createBookSet(List<BasketItemDTO> items, int setSize) {
        for (int i = 0; i < setSize; i++) {
            if (items.size() > i && items.get(i).quantity() > 0) {
                BasketItemDTO updatedItem = new BasketItemDTO(
                        items.get(i).bookId(),
                        items.get(i).quantity() - 1
                );
                items.set(i, updatedItem);
            }
        }
    }
}
