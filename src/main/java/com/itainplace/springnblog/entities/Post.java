package com.itainplace.springnblog.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.Instant;
@Entity
@Table
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column
    private String title;
    @Lob
    @NotEmpty
    @Column
    private String content;
    @Column
    private Instant createdOn;

    public Post() {
    }

    public Post(Long id, String title, String content, Instant createdOn, Instant updatedOn, String username) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Instant updatedOn;
    @NotBlank
    private String username;



}
