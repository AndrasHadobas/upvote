package hu.ponte.upvote.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import hu.ponte.upvote.domain.Idea;

import java.time.LocalDateTime;

public class IdeaListItemForAdmin {

    private Long id;

    private String name;

    private String description;

    private Long vote;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public IdeaListItemForAdmin(Idea idea) {
        this.id = idea.getId();
        this.name = idea.getName();
        this.description = idea.getDescription();
        this.vote = idea.getVote();
        this.createdAt = idea.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getVote() {
        return vote;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
