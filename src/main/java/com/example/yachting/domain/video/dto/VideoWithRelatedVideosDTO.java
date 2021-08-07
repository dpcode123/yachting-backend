package com.example.yachting.domain.video.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Data Transfer Object (DTO) for Video with related videos.
 * Videos are related by the same yacht and the same shipyard.
 * @author dp
 */
@Data
@AllArgsConstructor
public class VideoWithRelatedVideosDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private VideoDTO videoDTO;
    private List<VideoDTO> relatedVideosSameYacht;
    private List<VideoDTO> relatedVideosSameShipyard;
}
