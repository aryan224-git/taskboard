package com.aryan.taskboard.service;

import com.aryan.taskboard.model.BoardColumn;
import com.aryan.taskboard.repository.BoardColumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BoardColumnService {

    @Autowired
    private BoardColumnRepository boardColumnRepository;

    public BoardColumn createColumn(BoardColumn column) {
        return boardColumnRepository.save(column);
    }

    public List<BoardColumn> getColumnsByBoard(Long boardId) {
        return boardColumnRepository.findByBoardIdOrderByPosition(boardId);
    }

    public void deleteColumn(Long id) {
        boardColumnRepository.deleteById(id);
    }
}