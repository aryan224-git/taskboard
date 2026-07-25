package com.aryan.taskboard.service;

import com.aryan.taskboard.model.Comment;
import com.aryan.taskboard.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByCard(Long cardId) {
        return commentRepository.findByCardIdOrderByCreatedAtAsc(cardId);
    }
}