package com.aryan.taskboard.dto;

public class CardDTO {
    private Long id;
    private String title;
    private String description;
    private Long columnId;
    private Long assignedUserId;

    public CardDTO() {}

    public CardDTO(Long id, String title, String description, Long columnId, Long assignedUserId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.columnId = columnId;
        this.assignedUserId = assignedUserId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getColumnId() { return columnId; }
    public void setColumnId(Long columnId) { this.columnId = columnId; }

    public Long getAssignedUserId() { return assignedUserId; }
    public void setAssignedUserId(Long assignedUserId) { this.assignedUserId = assignedUserId; }
}