package com.aryan.taskboard.controller;

import com.aryan.taskboard.dto.CommentDTO;
import com.aryan.taskboard.model.Card;
import com.aryan.taskboard.model.Comment;
import com.aryan.taskboard.model.User;
import com.aryan.taskboard.repository.CardRepository;
import com.aryan.taskboard.service.CommentService;
import com.aryan.taskboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO dto) {
        Card card = cardRepository.findById(dto.getCardId())
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));
        User user = userService.getUserById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Comment comment = new Comment();
        comment.setText(dto.getText());
        comment.setCard(card);
        comment.setUser(user);

        Comment saved = commentService.addComment(comment);
        return ResponseEntity.ok(toDTO(saved));
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByCard(@PathVariable Long cardId) {
        List<CommentDTO> comments = commentService.getCommentsByCard(cardId)
                .stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }

    private CommentDTO toDTO(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getText(), comment.getCard().getId(), comment.getUser().getId());
    }
}
