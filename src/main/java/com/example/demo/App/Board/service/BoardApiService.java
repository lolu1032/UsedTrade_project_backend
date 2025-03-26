package com.example.demo.App.Board.service;

import com.example.demo.App.Auth.domain.Users;
import com.example.demo.App.Auth.repository.UserRepository;
import com.example.demo.App.Board.SearchSpecification;
import com.example.demo.App.Board.domain.Likes;
import com.example.demo.App.Board.domain.Product;
import com.example.demo.App.Board.dto.BoardReadDtos.*;
import com.example.demo.App.Board.repository.BoardSearchRepository;
import com.example.demo.App.Board.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardApiService {
    private final BoardSearchRepository boardRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public Page<Product> search(String keyword, Pageable pageable) {
        Specification<Product> search = SearchSpecification.search(keyword);
        return boardRepository.findAll(search,pageable);
    }

    public LikeResponse likes(LikeRequest likeRequest) {
        Product product = boardRepository.findById(likeRequest.productId())
                .orElseThrow();

        Users users = userRepository.findById(likeRequest.userId())
                .orElseThrow();

        Likes like = Likes.builder()
                .product(product)
                .status(likeRequest.status())
                .users(users)
                .build();

        likeRepository.save(like);

        return LikeResponse.builder()
                .status(like.getStatus())
                .build();
    }


    public LikeResponse likeView(Long userId,Long productId) {

        List<LikeResponse> like = likeRepository.findByUsersIdAndProductId(userId,productId);

        if(like.isEmpty()) {
            return LikeResponse.builder()
                    .status(false)
                    .build();
        }
        return LikeResponse.builder()
                .status(like.get(0).status())
                .build();
    }
}
