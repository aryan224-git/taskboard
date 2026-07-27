package com.aryan.taskboard.dto;

public class CommentDTO {
    private Long id;
    private String text;
    private Long cardId;
    private Long userId;

    public CommentDTO() {}

    public CommentDTO(Long id, String text, Long cardId, Long userId) {
        this.id = id;
        this.text = text;
        this.cardId = cardId;
        this.userId = userId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public Long getCardId() { return cardId; }
    public void setCardId(Long cardId) { this.cardId = cardId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}