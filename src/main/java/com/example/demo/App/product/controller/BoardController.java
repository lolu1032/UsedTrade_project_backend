package com.example.demo.App.product.controller;

import com.example.demo.App.product.domain.Product;
import com.example.demo.App.product.dto.BoardReadDtos.*;
import com.example.demo.App.product.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardRepository boardRepository;

    @GetMapping("/products")
    public ResponseEntity<Page<Board>> getProducts(@PageableDefault(page = 0, size = 20) Pageable pageable) {
        Page<Product> products = boardRepository.findAll(pageable);
        Page<Board> dtoPage = products.map(product -> new Board(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getViews(),
                product.getLocation().getRegionName(),
                product.getUser().getUsername(),
                product.getFirstImageUrl()
        ));
        return ResponseEntity.ok(dtoPage);
    }
}

