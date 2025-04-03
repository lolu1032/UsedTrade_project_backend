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
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;

    public CreateBoardResponse createBoard(CreateBoardRequest request) {
        Users user = userRepository.findById(request.userId())
                .orElseThrow(BoardErrorCode.USER_NOT_FOUND::exception);
        Location location = locationRepository.findById(request.locationId())
                .orElseThrow(BoardErrorCode.LOCATION_NOT_FOUND::exception);
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(BoardErrorCode.CATEGORY_NOT_FOUND::exception);

        Product product = Product.builder()
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .views(0)
                .status("SOLD")
                .category(category)
                .location(location)
                .user(user)
                .build();

        boardRepository.save(product);

        return CreateBoardResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .userId(user.getId())
                .locationId(location.getId())
                .categoryId(category.getId())
                .build();
    }


    public UpdateBoardResponse updateBoard(Long id,UpdateBoardRequest request) {
        Product product = boardRepository.findById(id)
                .orElseThrow(BoardErrorCode.BOARD_NOT_FOUND::exception);

        product.update(
                Optional.ofNullable(request.title()).orElse(product.getTitle()),
                Optional.ofNullable(request.description()).orElse(product.getDescription()),
                Optional.ofNullable(request.price()).orElse(product.getPrice())
        );

        boardRepository.save(product);

        return UpdateBoardResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public BoardResponse readOne(long id) {
        Product product = boardRepository.findById(id)
                .orElseThrow(BoardErrorCode.BOARD_NOT_FOUND::exception);

        product.updateViews();

        boardRepository.save(product);


        return BoardResponse.builder()
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
