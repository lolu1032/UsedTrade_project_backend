package com.example.demo.App.product.dto;

import lombok.Builder;

import java.math.BigDecimal;

public final class BoardReadDtos {
    @Builder
    public record Board(
            Long id,
            String title,
            BigDecimal price,
            int views,
            String username,
            String regionName,
            String imageUrl
    ) {
    }

    @Builder
    public record UpdateBoard(
            Long id,
            String title,
            String description,
            BigDecimal price
    ) {
    }
}