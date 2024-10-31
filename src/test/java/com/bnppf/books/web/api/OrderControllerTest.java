package com.bnppf.books.web.api;

import com.bnppf.books.service.OrderService;
import com.bnppf.books.web.exceptions.InvalidRequestException;
import com.bnppf.books.web.support.BasketDTO;
import com.bnppf.books.web.support.BasketItemDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
    @InjectMocks
    private OrderController orderController;
    @Mock
    private OrderService OrderService;

    @Test
    public void placeOrder_Successful() {
        BasketDTO basketDTO = new BasketDTO(LocalDateTime.now(), new ArrayList<>());
        basketDTO.items().add(new BasketItemDTO(1L, 1));
        when(OrderService.placeOrder(basketDTO)).thenReturn(50.0);

        ResponseEntity<Double> responseEntity = orderController.placeOrder(basketDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(50, responseEntity.getBody());

    }

    @Test
    public void placeOrder_emptyBasket_Unsuccessful() {
        BasketDTO basketDTO = new BasketDTO(LocalDateTime.now(), new ArrayList<>());

        when(OrderService.placeOrder(basketDTO)).thenThrow(InvalidRequestException.class);

        assertThrows(InvalidRequestException.class, () -> orderController.placeOrder(basketDTO));
    }

}