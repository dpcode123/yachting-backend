package com.example.yachting.domain.staticpage;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Static page entity class.
 * @author dp
 */
@Entity
@Table(name="static_pages")
@Data
@NoArgsConstructor
public class StaticPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="title")
    private String title;

    @Column(name="text")
    private String text;

    /**
     * Constructor without id.
     * @param name
     * @param title
     * @param text
     */
    public StaticPage(String name, String title, String text) {
        this.name = name;
        this.title = title;
        this.text = text;
    }
}