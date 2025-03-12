package com.example.demo.App.product.service;

import com.example.demo.App.product.domain.Product;
import com.example.demo.App.product.dto.BoardReadDtos.*;
import com.example.demo.App.product.exception.BoardErrorCode;
import com.example.demo.App.product.exception.BoardException;
import com.example.demo.App.product.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public UpdateBoard boardUpdate(Long id, String title, String description, BigDecimal price) {
        Product product = boardRepository.findById(id)
                .orElseThrow(BoardErrorCode.BOARD_NOT_FOUND::exception);

        product.update(title, description, price);

        boardRepository.save(product);

        return UpdateBoard.builder()
                .id(product.getId())
                .title(title)
                .description(description)
                .price(price)
                .build();
    }

    public void delete(Long id) {
        Product product = boardRepository.findById(id)
                .orElseThrow(BoardErrorCode.BOARD_NOT_FOUND::exception);

         boardRepository.deleteById(product.getId());
    }
}
