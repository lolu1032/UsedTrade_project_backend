package com.example.demo.Member.product.dto;

import com.example.demo.Member.Auth.domain.Users;
import com.example.demo.Member.product.domain.Location;
import lombok.Builder;

import java.math.BigDecimal;

public final class BoardReadDtos {
    @Builder
    public record Board(
            Long id,
            String title,
            BigDecimal price,
            int views,
            Users user,
            Location location
    ) {}
}
