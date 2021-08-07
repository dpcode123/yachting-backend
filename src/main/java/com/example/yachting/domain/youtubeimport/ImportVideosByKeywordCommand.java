package com.example.yachting.domain.youtubeimport;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Command for importing videos by keyword from youtube.
 * @author dp
 */
public class ImportVideosByKeywordCommand {
    /**
     * Maximum results per page. 1-50
     */
    @NotNull(message = "Max results is empty")
    private Integer maxResults;

    /**
     * Keyword to search by.
     */
    @NotBlank(message = "Keyword is empty")
    private String keyword;

    /**
     * Page token. Youtube api has pagination implemented
     * with page tokens and not page numbers.
     */
    @NotBlank(message = "Page token is empty")
    private String pageToken;

    /**
     * Yacht can be linked to video.
     */
    private Long yachtId;

    public ImportVideosByKeywordCommand(@JsonProperty("maxResults") Integer maxResults,
                                        @JsonProperty("keyword") String keyword,
                                        @JsonProperty("pageToken") String pageToken,
                                        @JsonProperty("yachtId") Long yachtId
                                ) {
        this.maxResults = maxResults;
        this.keyword = keyword;
        this.pageToken = pageToken;
        this.yachtId = yachtId;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getPageToken() {
        return pageToken;
    }

    public void setPageToken(String pageToken) {
        this.pageToken = pageToken;
    }

    public Long getYachtId() {
        return yachtId;
    }

    public void setYachtId(Long yachtId) {
        this.yachtId = yachtId;
    }
}
