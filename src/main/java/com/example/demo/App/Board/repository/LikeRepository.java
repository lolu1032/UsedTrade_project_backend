package com.example.demo.App.Board.repository;

import com.example.demo.App.Board.domain.Likes;
import com.example.demo.App.Board.dto.BoardReadDtos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Likes,Long> {
    List<BoardReadDtos.LikeResponse> findByUsersIdAndProductId(Long userId,Long productId);
}
