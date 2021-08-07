package com.example.yachting.domain.video.dto;

import com.example.yachting.domain.yacht.Yacht;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for Video class.
 * @author dp
 */
@Data
public class VideoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String youtubeId;
    private Yacht yacht;
    private LocalDateTime importedAt;
    private LocalDateTime activeUpdatedAt;
    private LocalDateTime publishedUpdatedAt;
}
