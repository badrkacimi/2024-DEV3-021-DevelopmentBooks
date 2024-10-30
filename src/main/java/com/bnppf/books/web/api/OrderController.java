package com.bnppf.books.web.api;


import com.bnppf.books.service.OrderService;
import com.bnppf.books.web.support.BasketDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public ResponseEntity<Double> placeOrder(@RequestBody BasketDTO basket) {
        double total = orderService.placeOrder(basket);
        return ResponseEntity.status(201).body(total);
    }
}
