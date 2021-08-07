package com.example.yachting.domain.shipyard;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Shipyard entity class.
 * @author dp
 */
@Entity
@Table(name="shipyards")
@Data
@NoArgsConstructor
public class Shipyard implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique id - primary key of a shipyard.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Shipyard's name.
     */
    @Column(name="name")
    private String name;

    /**
     * Country where shipyard is located.
     */
    @Column(name="country")
    private String country;

    /**
     * Year when shipyard is founded.
     */
    @Column(name="year_founded")
    private Integer yearFounded;

    /**
     * Url of a shipyard's website.
     */
    @Column(name="website_url")
    private String websiteUrl;

    /**
     * Url of a shipyard's youtube channel.
     */
    @Column(name="youtube_channel_url")
    private String youtubeChannelUrl;

    /**
     * Constructor without id.
     * @param name
     * @param country
     * @param yearFounded
     * @param websiteUrl
     * @param youtubeChannelUrl
     */
    public Shipyard(String name, String country, Integer yearFounded, String websiteUrl, String youtubeChannelUrl) {
        this.name = name;
        this.country = country;
        this.yearFounded = yearFounded;
        this.websiteUrl = websiteUrl;
        this.youtubeChannelUrl = youtubeChannelUrl;
    }

}
