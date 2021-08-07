package com.example.yachting.domain.video;

import com.example.yachting.domain.yacht.Yacht;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * Video command for editing an existing video.
 * New videos are added by importing from youtube api.
 * @see com.example.yachting.domain.youtubeimport.ImportVideoServiceImpl
 * @author dp
 */
public class VideoCommand {

    @NotBlank(message = "Title is empty")
    private String title;

    @NotBlank(message = "Youtube id is empty")
    private String youtubeId;

    private Long yachtId;

    /**
     * VideoCommand constructor.
     * @param title
     * @param youtubeId
     * @param yachtId
     */
    public VideoCommand(@JsonProperty("title") String title,
                        @JsonProperty("youtubeId") String youtubeId,
                        @JsonProperty("yachtId") Long yachtId) {
        this.title = title;
        this.youtubeId = youtubeId;
        this.yachtId = yachtId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }

    public Long getYachtId() {
        return yachtId;
    }

    public void setYachtId(Long yachtId) {
        this.yachtId = yachtId;
    }
}
