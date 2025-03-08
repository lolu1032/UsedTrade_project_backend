package com.example.demo.App.product.dto;

import com.example.demo.App.Auth.domain.Users;
import com.example.demo.App.product.domain.Location;
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
}
