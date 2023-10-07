package com.itainplace.springnblog.dto;

import com.itainplace.springnblog.entities.User;

public class RegisterResponse {

    private String username;
    private String email;

    public RegisterResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public RegisterResponse(User entity) {
        username = entity.getUsername();
        email = entity.getEmail();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
