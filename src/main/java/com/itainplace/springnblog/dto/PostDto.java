package com.itainplace.springnblog.dto;

import com.itainplace.springnblog.entities.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;

public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Instant createdOn;
    private String username;

    public String getUsername() {
        return username;
    }


    public PostDto(Long id, String title, String content, Instant createdOn, String username) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdOn = createdOn;
        this.username = username;
    }

    public PostDto(Post entity) {
        id = entity.getId();
        title = entity.getTitle();
        content = entity.getContent();
        createdOn = entity.getCreatedOn();
        username= entity.getUsername();
    }
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }
}
