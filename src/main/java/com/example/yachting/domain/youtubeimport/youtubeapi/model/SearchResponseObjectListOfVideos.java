package com.example.yachting.domain.youtubeimport.youtubeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Response from youtube api: search by keyword (multiple videos).
 */
public class SearchResponseObjectListOfVideos {

    private String kind;
    private String etag;
    private String regionCode;
    private String nextPageToken;

    /**
     * List of videos.
     */
    @JsonProperty("items")
    private List<YoutubeVideoFromList> jsonItems;

    public SearchResponseObjectListOfVideos(String kind, String etag, String regionCode, List<YoutubeVideoFromList> jsonItems, String nextPageToken) {
        this.kind = kind;
        this.etag = etag;
        this.regionCode = regionCode;
        this.jsonItems = jsonItems;
        this.nextPageToken = nextPageToken;
    }

    public SearchResponseObjectListOfVideos() {
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

    public List<YoutubeVideoFromList> getJsonItems() {
        return jsonItems;
    }

    public void setJsonItems(List<YoutubeVideoFromList> jsonItems) {
        this.jsonItems = jsonItems;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }
}
