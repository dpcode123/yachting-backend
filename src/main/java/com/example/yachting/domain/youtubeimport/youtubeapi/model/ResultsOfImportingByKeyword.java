package com.example.yachting.domain.youtubeimport.youtubeapi.model;

import java.util.List;

/**
 * Results of importing videos by keyword from youtube api.
 * @author dp
 */
public class ResultsOfImportingByKeyword {
    /**
     * List of youtube videos fetched from search by keyword.
     */
    private List<YoutubeVideoFromList> videos;

    /**
     * Number of videos fetched from api.
     */
    private Integer videosFetchedFromApi;

    /**
     * Number of fetched videos that were imported before.
     */
    private Integer videosAlreadyInDatabase;

    /**
     * Number of videos that are imported with this action.
     */
    private Integer videosImported;

    /**
     * Next page token.
     * Youtube api pagination is implemented with page tokens
     * and not page numbers.
     */
    private String nextPageToken;

    /**
     * Constructor.
     * @param videos
     * @param videosFetchedFromApi
     * @param videosAlreadyInDatabase
     * @param videosImported
     * @param nextPageToken
     */
    public ResultsOfImportingByKeyword(List<YoutubeVideoFromList> videos, Integer videosFetchedFromApi, Integer videosAlreadyInDatabase, Integer videosImported, String nextPageToken) {
        this.videos = videos;
        this.videosFetchedFromApi = videosFetchedFromApi;
        this.videosAlreadyInDatabase = videosAlreadyInDatabase;
        this.videosImported = videosImported;
        this.nextPageToken = nextPageToken;
    }

    public ResultsOfImportingByKeyword() {
    }

    public List<YoutubeVideoFromList> getVideos() {
        return videos;
    }

    public void setVideos(List<YoutubeVideoFromList> videos) {
        this.videos = videos;
    }

    public Integer getVideosFetchedFromApi() {
        return videosFetchedFromApi;
    }

    public void setVideosFetchedFromApi(Integer videosFetchedFromApi) {
        this.videosFetchedFromApi = videosFetchedFromApi;
    }

    public Integer getVideosAlreadyInDatabase() {
        return videosAlreadyInDatabase;
    }

    public void setVideosAlreadyInDatabase(Integer videosAlreadyInDatabase) {
        this.videosAlreadyInDatabase = videosAlreadyInDatabase;
    }

    public Integer getVideosImported() {
        return videosImported;
    }

    public void setVideosImported(Integer videosImported) {
        this.videosImported = videosImported;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }
}
