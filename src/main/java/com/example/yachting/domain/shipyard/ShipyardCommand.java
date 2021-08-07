package com.example.yachting.domain.shipyard;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * Shipyard command for adding/editing a shipyard.
 * @author dp
 */
public class ShipyardCommand {

    @NotBlank(message = "Name is empty")
    private String name;
    private String country;
    private Integer yearFounded;
    private String websiteUrl;
    private String youtubeChannelUrl;

    /**
     * ShipyardCommand constructor.
     * @param name
     * @param country
     * @param yearFounded
     * @param websiteUrl
     * @param youtubeChannelUrl
     */
    public ShipyardCommand(@JsonProperty("name") String name,
                           @JsonProperty("country") String country,
                           @JsonProperty("yearFounded") Integer yearFounded,
                           @JsonProperty("websiteUrl") String websiteUrl,
                           @JsonProperty("youtubeChannelUrl") String youtubeChannelUrl) {
        this.name = name;
        this.country = country;
        this.yearFounded = yearFounded;
        this.websiteUrl = websiteUrl;
        this.youtubeChannelUrl = youtubeChannelUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getYearFounded() {
        return yearFounded;
    }

    public void setYearFounded(Integer yearFounded) {
        this.yearFounded = yearFounded;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getYoutubeChannelUrl() {
        return youtubeChannelUrl;
    }

    public void setYoutubeChannelUrl(String youtubeChannelUrl) {
        this.youtubeChannelUrl = youtubeChannelUrl;
    }
}
