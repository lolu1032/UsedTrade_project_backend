package com.example.demo.App.Board;

import com.example.demo.App.Board.domain.Product;
import org.springframework.data.jpa.domain.Specification;

public class SearchSpecification {
    public static Specification<Product> search(String keyword) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(root.get("title"),'%' + keyword + '%')
                ));
    }
}
