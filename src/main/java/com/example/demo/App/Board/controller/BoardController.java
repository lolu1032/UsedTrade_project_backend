package com.example.demo.App.Board.controller;

import com.example.demo.App.Board.domain.Product;
import com.example.demo.App.Board.dto.BoardReadDtos.*;
import com.example.demo.App.Board.repository.BoardRepository;
import com.example.demo.App.Board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/boards")
public class BoardController {
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @GetMapping("/products")
    public Page<Product> getProducts(@PageableDefault(page = 0, size = 20) Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @GetMapping("/products/{id}")
    public Board readOne(@PathVariable long id) {
        return boardService.readOne(id);
    }

    @PostMapping
    public CreateBoard createBoard(@RequestBody @Valid CreateBoard createBoard) {
        return boardService.createBoard(createBoard.title(),createBoard.description(),createBoard.price(),createBoard.userId(),createBoard.locationId(),createBoard.categoryId());
    }


    @PutMapping("{id}")
    public UpdateBoard updateBoard(
            @PathVariable Long id,
            @RequestBody UpdateBoard updateBoard)
    {
        return boardService.updateBoard(id, updateBoard.title(),updateBoard.description(),updateBoard.price());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        boardService.delete(id);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }

}

