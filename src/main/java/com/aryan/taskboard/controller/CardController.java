package com.aryan.taskboard.controller;

import com.aryan.taskboard.dto.CardDTO;
import com.aryan.taskboard.model.BoardColumn;
import com.aryan.taskboard.model.Card;
import com.aryan.taskboard.model.User;
import com.aryan.taskboard.repository.BoardColumnRepository;
import com.aryan.taskboard.service.CardService;
import com.aryan.taskboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private BoardColumnRepository boardColumnRepository;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<CardDTO> createCard(@RequestBody CardDTO dto) {
        BoardColumn column = boardColumnRepository.findById(dto.getColumnId())
                .orElseThrow(() -> new IllegalArgumentException("Column not found"));
        Card card = new Card();
        card.setTitle(dto.getTitle());
        card.setDescription(dto.getDescription());
        card.setColumn(column);
        if (dto.getAssignedUserId() != null) {
            User assignee = userService.getUserById(dto.getAssignedUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            card.setAssignedUser(assignee);
        }
        Card saved = cardService.createCard(card);
        return ResponseEntity.ok(toDTO(saved));
    }

    @GetMapping("/column/{columnId}")
    public ResponseEntity<List<CardDTO>> getCardsByColumn(@PathVariable Long columnId) {
        List<CardDTO> cards = cardService.getCardsByColumn(columnId)
                .stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(cards);
    }

    @PutMapping("/{cardId}/move/{newColumnId}")
    public ResponseEntity<CardDTO> moveCard(@PathVariable Long cardId, @PathVariable Long newColumnId) {
        Card moved = cardService.moveCard(cardId, newColumnId);
        return ResponseEntity.ok(toDTO(moved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }

    private CardDTO toDTO(Card card) {
        Long assignedId = card.getAssignedUser() != null ? card.getAssignedUser().getId() : null;
        return new CardDTO(card.getId(), card.getTitle(), card.getDescription(),
                card.getColumn().getId(), assignedId);
    }
}