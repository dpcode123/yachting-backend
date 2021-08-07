package com.example.yachting.domain.youtubeimport.youtubeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Response from youtube api: search video by id (single video).
 */
public class SearchResponseObjectSingleVideoDetails {

    private String kind;
    private String etag;
    private String regionCode;

    /**
     * Video.
     * Although response contains only a single video, it is still in list.
     */
    @JsonProperty("items")
    private List<YoutubeVideoFromDetails> jsonItems;

    public SearchResponseObjectSingleVideoDetails(String kind, String etag, String regionCode, List<YoutubeVideoFromDetails> jsonItems) {
        this.kind = kind;
        this.etag = etag;
        this.regionCode = regionCode;
        this.jsonItems = jsonItems;
    }

    public SearchResponseObjectSingleVideoDetails() {
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public List<YoutubeVideoFromDetails> getJsonItems() {
        return jsonItems;
    }

    public void setJsonItems(List<YoutubeVideoFromDetails> jsonItems) {
        this.jsonItems = jsonItems;
    }
}
