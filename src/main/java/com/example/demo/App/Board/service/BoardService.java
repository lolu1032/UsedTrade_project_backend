package com.example.demo.App.Board.service;

import com.example.demo.App.Auth.domain.Users;
import com.example.demo.App.Auth.repository.UserRepository;
import com.example.demo.App.Board.domain.Category;
import com.example.demo.App.Board.domain.Location;
import com.example.demo.App.Board.domain.Product;
import com.example.demo.App.Board.dto.BoardReadDtos.*;
import com.example.demo.App.Board.exception.BoardErrorCode;
import com.example.demo.App.Board.repository.BoardRepository;
import com.example.demo.App.Board.repository.CategoryRepository;
import com.example.demo.App.Board.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;

    public CreateBoard createBoard(
            String title, String description,
            BigDecimal price, Long userId,
            Long locationId, Long categoryId
    ) {
        Users user = userRepository.findById(userId)
                .orElseThrow(BoardErrorCode.USER_NOT_FOUND::exception);
        Location location = locationRepository.findById(locationId)
                .orElseThrow(BoardErrorCode.LOCATION_NOT_FOUND::exception);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(BoardErrorCode.CATEGORY_NOT_FOUND::exception);

        Product product = Product.builder()
                .title(title)
                .description(description)
                .price(price)
                .views(0)
                .status("SOLD")
                .category(category)
                .location(location)
                .user(user)
                .build();

        boardRepository.save(product);

        return CreateBoard.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .userId(userId)
                .locationId(locationId)
                .categoryId(categoryId)
                .build();
    }


    public UpdateBoard updateBoard(Long id, String title, String description, BigDecimal price) {
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

    public Board readOne(long id) {
        Product product = boardRepository.findById(id)
                .orElseThrow(BoardErrorCode.BOARD_NOT_FOUND::exception);

        product.updateViews();

        boardRepository.save(product);


        return Board.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .views(product.getViews())
                .username(product.getUser().getUsername())
                .regionName(product.getLocation().getRegionName())
                .imageUrl(product.getImages().isEmpty() ? null : product.getImages().get(0).getImageUrl())
                .build();
    }
}
