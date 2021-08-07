package com.example.yachting.security.auth.authority;

import com.example.yachting.security.auth.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Authority class.
 */
@Entity
@Table(name="authority")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(targetEntity = User.class, mappedBy = "authorities", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    public Authority(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }

    public Authority() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
