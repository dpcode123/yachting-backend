package com.example.yachting.domain.video.controller;

import com.example.yachting.domain.video.VideoService;
import com.example.yachting.domain.video.dto.VideoDTO;
import com.example.yachting.domain.video.dto.VideoWithRelatedVideosDTO;
import com.example.yachting.domain.video.page.VideosPagePublic;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Video controller - PUBLIC endpoints.
 * @author dp
 */
@RestController
@CrossOrigin(origins = {"${client.origin}"})
@RequestMapping(path = "api/video/public")
@RequiredArgsConstructor
public class VideoControllerPublic {

    private final VideoService videoService;

    /**
     * Gets a single CACHEABLE video with related videos.
     * @param id
     * @return response entity containing a VideoWithRelatedVideosDTO.
     */
    @GetMapping(path = "/details/{id}")
    public ResponseEntity<VideoWithRelatedVideosDTO> getVideoWithRelatedVideosCacheable(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(videoService.getCacheableVideoWithRelatedVideos(id));
    }

    /**
     * Gets CACHEABLE paginated videos for displaying to user on a PUBLIC page.
     * @param videosPagePublic
     * @return response entity containing a page of VideoDTOs.
     */
    @GetMapping(path = "/paginated")
    public ResponseEntity<Page<VideoDTO>> findAllPaginated(VideosPagePublic videosPagePublic) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(videoService.getCacheablePaginatedPublicVideosPage(videosPagePublic));
    }

    /**
     * Gets a single video.
     * @param id
     * @return response entity containing a VideoDTO.
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<VideoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(videoService.findVideoById(id));
    }

    /**
     * Gets a single video with related videos.
     * @param id
     * @return response entity containing a VideoWithRelatedVideosDTO.
     */
    @GetMapping(path = "/details-nocache/{id}")
    public ResponseEntity<VideoWithRelatedVideosDTO> getVideoWithRelatedVideos(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(videoService.getVideoWithRelatedVideos(id));
    }


    /**
     * Gets videos by yacht.
     * @param yachtId
     * @return response entity containing a list of VideoDTOs.
     */
    @GetMapping(path = "/find-by-yacht/{yachtId}")
    public ResponseEntity<List<VideoDTO>> findByYacht(@PathVariable Long yachtId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(videoService.findVideosByYachtId(yachtId));
    }

    /**
     * Gets videos by yacht's shipyard.
     * @param shipyardId
     * @return response entity containing a list of VideoDTOs.
     */
    @GetMapping(path = "/find-by-shipyard/{shipyardId}")
    public ResponseEntity<List<VideoDTO>> findByShipyard(@PathVariable Long shipyardId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(videoService.findVideosByYachtShipyardId(shipyardId));
    }

}
