package com.example.yachting.security.auth.user;

import java.util.Set;

/**
 * User Data Transfer Object (DTO).
 */
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Set<String> authorities;

    public UserDTO(Long id, String username, String email, Set<String> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
}
