package com.bnppf.books.web.support;

import java.time.LocalDateTime;
import java.util.List;

public record BasketDTO(LocalDateTime orderDate, List<BasketItemDTO> items) {
}
