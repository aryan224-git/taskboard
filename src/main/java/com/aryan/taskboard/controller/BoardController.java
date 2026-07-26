package com.aryan.taskboard.controller;

import com.aryan.taskboard.dto.BoardDTO;
import com.aryan.taskboard.model.Board;
import com.aryan.taskboard.model.User;
import com.aryan.taskboard.service.BoardService;
import com.aryan.taskboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<BoardDTO> createBoard(@RequestBody BoardDTO dto) {
        User owner = userService.getUserById(dto.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
        Board board = new Board();
        board.setName(dto.getName());
        board.setOwner(owner);
        Board saved = boardService.createBoard(board);
        return ResponseEntity.ok(toDTO(saved));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<BoardDTO>> getBoardsByOwner(@PathVariable Long ownerId) {
        List<BoardDTO> boards = boardService.getBoardsByOwner(ownerId)
                .stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoard(@PathVariable Long id) {
        return boardService.getBoardById(id)
                .map(board -> ResponseEntity.ok(toDTO(board)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }

    private BoardDTO toDTO(Board board) {
        return new BoardDTO(board.getId(), board.getName(), board.getOwner().getId());
    }
}