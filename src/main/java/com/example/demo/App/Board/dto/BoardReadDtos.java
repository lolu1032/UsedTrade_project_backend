package com.example.demo.App.Board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;

public final class BoardReadDtos {
    @Builder
    public record BoardResponse(
            Long id,
            String title,
            String description,
            BigDecimal price,
            int views,
            String username,
            String regionName,
            String imageUrl
    ) {
    }

    @Builder
    public record UpdateBoardResponse(
            Long id,
            String title,
            String description,
            BigDecimal price
    ) {
    }

    @Builder
    public record UpdateBoardRequest(
            Long id,
            String title,
            String description,
            BigDecimal price
    ) {
    }


    @Builder
    public record CreateBoardResponse(
            Long id,
            String title,
            String description,
            BigDecimal price,
            Long userId,
            Long locationId,
            Long categoryId
    ) {
    }

    @Builder
    public record CreateBoardRequest(
            @NotBlank(message = "제목을 입력해주세요.")
            @Size(min = 2, message = "두 글자 이상 입력하세요.")
            @Size(max = 30, message = "제목은 최대 30글자입니다.")
            String title,

            @NotBlank(message = "물품 설명을 입력해주세요.")
            @Size(min = 2, message = "두 글자 이상 입력하세요.")
            @Size(max = 300, message = "제목은 최대 300글자입니다.")
            String description,

            @NotNull(message = "가격을 입력하세요.")
            BigDecimal price,
            Long userId,
            Long locationId,
            Long categoryId

    ) {
    }

    @Builder
    public record LikeRequest(
            Long userId,
            Long productId,
            boolean status
    ) {
    }

    @Builder
    public record LikeResponse(
            boolean status
    ) {
    }

    @Builder
    public record readAllUserBoardRequest(
            Long userId
    ) {
    }
}