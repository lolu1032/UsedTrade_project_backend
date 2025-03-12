package com.example.demo.App.product.controller;

import com.example.demo.App.product.domain.Product;
import com.example.demo.App.product.dto.BoardReadDtos.*;
import com.example.demo.App.product.exception.BoardErrorCode;
import com.example.demo.App.product.repository.BoardRepository;
import com.example.demo.App.product.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;


@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardRepository boardRepository;
    private final BoardService boardService;

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

    @GetMapping("/products/{id}")
    public Board readOne(@PathVariable long id) {
        Product product = boardRepository.findById(id)
                .orElseThrow(BoardErrorCode.BOARD_NOT_FOUND::exception);

        return Board.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .views(product.getViews())
                .username(product.getUser().getUsername())
                .regionName(product.getLocation().getRegionName())
                .imageUrl(product.getImages().isEmpty() ? null : product.getImages().get(0).getImageUrl())  // 첫 번째 이미지 URL
                .build();
    }

    @PostMapping("{id}")
    public UpdateBoard boardUpdate(
            @PathVariable Long id,
            @RequestBody UpdateBoard updateBoard)
    {
        return boardService.boardUpdate(id, updateBoard.title(),updateBoard.description(),updateBoard.price());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        boardService.delete(id);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}

