package com.example.demo.App.Board.controller;

import com.example.demo.App.Board.domain.Product;
import com.example.demo.App.Board.service.BoardApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BoardApiController {
    private final BoardApiService service;

    @GetMapping("/search")
    public Page<Product> search(
            @RequestParam String keyword,
            Pageable pageable
    ) {
        return service.search(keyword, pageable);
    }
}
