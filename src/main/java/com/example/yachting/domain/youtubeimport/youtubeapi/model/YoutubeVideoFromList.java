package com.example.yachting.domain.youtubeimport.youtubeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/*
{
    "kind": "youtube#searchResult",
    "id": {
        "kind": "youtube#video",
        "videoId": "ma67y5dMQfsId123"
    },
    "snippet": {
        "publishedAt": "2021-01-23T17:00:15Z",
        "channelId": "sa231asd51ad213das82ab",
        "title": "Lorem ipsum...",
        "description": "Lorem ipsum description...",
        "channelTitle": "Lorem Ipsum Channel"
    }
}
* */
public class YoutubeVideoFromList {

    private String kind;

    /**
     * Youtube video id.
     *
     */
    private String videoId;

    /**
     * Video title.
     */
    private String title;

    /**
     * Kind.
     */
    private String videoKind;

    /**
     * When was video published on youtube.
     */
    private LocalDateTime publishedAt;

    /**
     * Youtube channel id.
     */
    private String channelId;

    /**
     * Youtube channel title.
     */
    private String channelTitle;


    /**
     * Constructor.
     * @param kind
     * @param videoId
     * @param title
     * @param videoKind
     * @param publishedAt
     * @param channelId
     * @param channelTitle
     */
    public YoutubeVideoFromList(String kind, String videoId, String title, String videoKind, LocalDateTime publishedAt, String channelId, String channelTitle) {
        this.kind = kind;
        this.videoId = videoId;
        this.title = title;
        this.videoKind = videoKind;
        this.publishedAt = publishedAt;
        this.channelId = channelId;
        this.channelTitle = channelTitle;
    }

    public YoutubeVideoFromList() {
    }

    /**
     * Unpacking nested JSON properties.
     * @param jsonObject
     */
    @JsonProperty("id")
    private void unpackNestedId(Map<String, Object> jsonObject) {
        this.videoKind = (String)jsonObject.get("kind");
        this.videoId = (String)jsonObject.get("videoId");
    }

    /**
     * Unpacking nested JSON properties.
     * @param jsonObject
     */
    @JsonProperty("snippet")
    private void unpackNestedSnippet(Map<String, Object> jsonObject) throws ParseException {
        this.title = (String)jsonObject.get("title");
        this.channelId = (String)jsonObject.get("channelId");
        this.channelTitle = (String)jsonObject.get("channelTitle");

        String publishedAtString = (String)jsonObject.get("publishedAt");
        String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        Date date = simpleDateFormat.parse(publishedAtString);
        Timestamp timestamp = new Timestamp(date.getTime());
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        this.publishedAt = localDateTime;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoKind() {
        return videoKind;
    }

    public void setVideoKind(String videoKind) {
        this.videoKind = videoKind;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }
}
