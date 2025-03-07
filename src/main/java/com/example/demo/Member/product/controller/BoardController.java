package com.example.demo.Member.product.controller;

import com.example.demo.Member.product.domain.Product;
import com.example.demo.Member.product.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public Page<Product> getProducts( @PageableDefault(page = 0, size = 5) Pageable pageable) {
        return boardRepository.findAll(pageable); // Pageable을 그대로 활용한 페이징 처리
    }
}

