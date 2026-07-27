package com.aryan.taskboard.controller;

import com.aryan.taskboard.dto.BoardColumnDTO;
import com.aryan.taskboard.model.Board;
import com.aryan.taskboard.model.BoardColumn;
import com.aryan.taskboard.repository.BoardRepository;
import com.aryan.taskboard.service.BoardColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/columns")
public class BoardColumnController {

    @Autowired
    private BoardColumnService boardColumnService;

    @Autowired
    private BoardRepository boardRepository;

    @PostMapping
    public ResponseEntity<BoardColumnDTO> createColumn(@RequestBody BoardColumnDTO dto) {
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        BoardColumn column = new BoardColumn();
        column.setName(dto.getName());
        column.setPosition(dto.getPosition() != null ? dto.getPosition() : 0);
        column.setBoard(board);

        BoardColumn saved = boardColumnService.createColumn(column);
        return ResponseEntity.ok(toDTO(saved));
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<BoardColumnDTO>> getColumnsByBoard(@PathVariable Long boardId) {
        List<BoardColumnDTO> columns = boardColumnService.getColumnsByBoard(boardId)
                .stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(columns);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColumn(@PathVariable Long id) {
        boardColumnService.deleteColumn(id);
        return ResponseEntity.noContent().build();
    }

    private BoardColumnDTO toDTO(BoardColumn column) {
        return new BoardColumnDTO(column.getId(), column.getName(), column.getPosition(), column.getBoard().getId());
    }
}