package com.bnppf.books.service;


import com.bnppf.books.web.exceptions.InvalidRequestException;
import com.bnppf.books.web.support.BasketDTO;
import com.bnppf.books.web.support.BasketItemDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    OrderService OrderService;

    @Test
    public void calculatePrice_SingleBook_NoDiscount() {
        BasketDTO basketDTO = new BasketDTO(LocalDateTime.now(), new ArrayList<>());
        basketDTO.items().add(new BasketItemDTO(1L, 1));

        double price = OrderService.placeOrder(basketDTO);

        assertEquals(50.0, price, "Price for a single book should be 50.0");
    }

    @Test
    public void calculatePrice_TwoSameBooks_NoDiscount() {
        BasketDTO basketDTO = new BasketDTO(LocalDateTime.now(), new ArrayList<>());
        basketDTO.items().add(new BasketItemDTO(1L, 2));

        double price = OrderService.placeOrder(basketDTO);

        assertEquals(100.0, price, "Price for a two same book should be 100.0");

    }

    @Test
    public void calculatePrice_TwoDifferentBooks_AppliesDiscount() {
        BasketDTO basketDTO = new BasketDTO(LocalDateTime.now(), new ArrayList<>());
        basketDTO.items().add(new BasketItemDTO(1L, 1));
        basketDTO.items().add(new BasketItemDTO(2L, 1));

        double price = OrderService.placeOrder(basketDTO);

        assertEquals(95, price);
    }

    @Test
    public void calculatePrice_FiveDifferentBooks_AppliesDiscount() {
        BasketDTO basketDTO = new BasketDTO(LocalDateTime.now(), new ArrayList<>());
        basketDTO.items().add(new BasketItemDTO(1L, 1));
        basketDTO.items().add(new BasketItemDTO(2L, 1));
        basketDTO.items().add(new BasketItemDTO(3L, 1));
        basketDTO.items().add(new BasketItemDTO(4L, 1));
        basketDTO.items().add(new BasketItemDTO(5L, 1));

        double price = OrderService.placeOrder(basketDTO);
        assertEquals(187.5, price);
    }

    @Test
    public void calculatePrice_DifferentBooks_AppliesDiscount() {
        BasketDTO basketDTO = new BasketDTO(LocalDateTime.now(), new ArrayList<>());
        basketDTO.items().add(new BasketItemDTO(1L, 2));
        basketDTO.items().add(new BasketItemDTO(2L, 2));
        basketDTO.items().add(new BasketItemDTO(3L, 2));
        basketDTO.items().add(new BasketItemDTO(4L, 1));
        basketDTO.items().add(new BasketItemDTO(5L, 1));

        double price = OrderService.placeOrder(basketDTO);

        assertEquals(320, price);
    }

    @Test
    public void calculatePrice_ShouldThrowException_WhenBasketIsEmpty() {
        BasketDTO emptyBasket = new BasketDTO(LocalDateTime.now(), new ArrayList<>());

        InvalidRequestException thrownException = assertThrows(
                InvalidRequestException.class,
                () -> OrderService.placeOrder(emptyBasket));

        assertEquals("Basket cannot be empty.", thrownException.getMessage());
    }

    @Test
    public void calculatePrice_ShouldThrowException_WhenQuantityIsNegative() {
        BasketDTO basketDTO = new BasketDTO(LocalDateTime.now(), new ArrayList<>());
        basketDTO.items().add(new BasketItemDTO(1L, 2));
        basketDTO.items().add(new BasketItemDTO(2L, -3));

        InvalidRequestException thrownException = assertThrows(
                InvalidRequestException.class,
                () -> OrderService.placeOrder(basketDTO));

        assertEquals("Book quantity cannot be negative.", thrownException.getMessage());
    }
}
