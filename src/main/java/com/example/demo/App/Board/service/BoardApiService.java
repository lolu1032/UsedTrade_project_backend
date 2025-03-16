package com.example.demo.App.Board.service;

import com.example.demo.App.Board.SearchSpecification;
import com.example.demo.App.Board.domain.Product;
import com.example.demo.App.Board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardApiService {
    private final BoardRepository boardRepository;

    public Page<Product> search(String keyword, Pageable pageable) {
        Specification<Product> search = SearchSpecification.search(keyword);
        return boardRepository.findAll(search,pageable);
    }

}
