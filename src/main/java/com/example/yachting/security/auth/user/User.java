package com.example.yachting.security.auth.user;

import com.example.yachting.security.auth.authority.Authority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity class.
 */
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @ManyToMany(targetEntity = Authority.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_authority",
            joinColumns = { @JoinColumn(name="user_id")},
            inverseJoinColumns = { @JoinColumn(name="authority_id")}
    )
    private Set<Authority> authorities = new HashSet<>();

    public User() { }

    public User(String username, String email, String password, Set<Authority> authorities) {
        this.username = username;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
