package com.example.yachting.domain.youtubeimport.youtubeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/*
        "kind": "youtube#videoListResponse",
          "etag": "xRMTfdxUjk2Xf53dsl43IHj",
          "items": [
            {
              "kind": "youtube#video",
              "etag": "xxx",
              "id": "yyysssfffssss",
              "snippet": {
                "publishedAt": "2012-10-01T15:27:35Z",
                "channelId": "lflasflsaslas",
                "title": "Lorem Ipsum",
                ...
* */
public class YoutubeVideoFromDetails {

    /**
     * Kind.
     */
    private String kind;

    /**
     * Youtube video id.
     */
    @JsonProperty("id")
    private String videoId;

    /**
     * When was video published on youtube.
     */
    private String publishedAt;

    /**
     * Youtube channel id.
     */
    private String channelId;

    /**
     * Video title.
     */
    private String title;

    /**
     * Constructor.
     * @param kind
     * @param videoId
     * @param publishedAt
     * @param channelId
     * @param title
     */
    public YoutubeVideoFromDetails(String kind, String videoId, String publishedAt, String channelId, String title) {
        this.kind = kind;
        this.videoId = videoId;
        this.publishedAt = publishedAt;
        this.channelId = channelId;
        this.title = title;
    }

    /**
     * Unpacking nested JSON properties.
     * @param jsonObject
     */
    @JsonProperty("snippet")
    private void unpackNestedSnippet(Map<String, Object> jsonObject) {
        this.publishedAt = (String)jsonObject.get("publishedAt");
        this.channelId = (String)jsonObject.get("channelId");
        this.title = (String)jsonObject.get("title");
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
