package com.example.yachting.domain.video.controller;

import com.example.yachting.domain.common.EntityStatus;
import com.example.yachting.domain.video.VideoCommand;
import com.example.yachting.domain.video.page.VideosPageAdmin;
import com.example.yachting.domain.video.VideoService;
import com.example.yachting.domain.video.dto.VideoDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Video controller - ADMIN endpoints.
 * @author dp
 */
@RestController
@CrossOrigin(origins = {"${client.origin}"})
@RequestMapping(path = "api/video/admin")
public class VideoControllerAdmin {

    private final VideoService videoService;

    public VideoControllerAdmin(VideoService videoService) {
        this.videoService = videoService;
    }

    /**
     * Gets all videos.
     * @return response entity containing a list of VideoDTOs.
     */
    @Secured({"ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<List<VideoDTO>> findAll() { return videoService.findAllVideos(); }

    /**
     * Gets all imported (and not yet published or removed) videos.
     * @return response entity containing a list of VideoDTOs.
     */
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/imported")
    public ResponseEntity<List<VideoDTO>> findImportedVideos() { return videoService.findImportedVideos(); }

    /**
     * Gets all published videos.
     * @return response entity containing a list of VideoDTOs.
     */
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/published")
    public ResponseEntity<List<VideoDTO>> findPublishedVideos() { return videoService.findPublishedVideos(); }

    /**
     * Gets all inactive(removed) videos.
     * @return response entity containing a list of VideoDTOs.
     */
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/inactive")
    public ResponseEntity<List<VideoDTO>> findInactiveVideos() { return videoService.findInactiveVideos(); }

    /**
     * Gets paginated videos filtered by entity status.
     * For displaying videos in ADMIN interface.
     * @param videosPageAdmin
     * @param entityStatus
     * @return response entity containing a page of VideoDTOs.
     */
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/filtered-paginated")
    public ResponseEntity<Page<VideoDTO>> findImportedVideosPaginated(VideosPageAdmin videosPageAdmin, EntityStatus entityStatus) {
        return videoService.getPaginatedVideosAdminFilteredByEntityStatus(videosPageAdmin, entityStatus);
    }

    /**
     * Updates video's details.
     * @param videoId
     * @param videoCommand
     * @return response entity containing a VideoDTO.
     */
    @Secured({"ROLE_ADMIN"})
    @PutMapping("/edit/{videoId}")
    public ResponseEntity<VideoDTO> editVideo(@PathVariable Long videoId, @Valid @RequestBody final VideoCommand videoCommand) {
        return videoService.editVideo(videoId, videoCommand);
    }

    /**
     * Updates video's activation status.
     * @param videoId
     * @param activationAction
     * @return response entity containing a VideoDTO.
     */
    @Secured({"ROLE_ADMIN"})
    @PatchMapping("/activation/{videoId}")
    public ResponseEntity<VideoDTO> videoActivation(@PathVariable Long videoId, @Valid @RequestBody final String activationAction) {
        return videoService.videoActivation(videoId, activationAction);
    }

    /**
     * Updates video's publishing status.
     * @param videoId
     * @param publishingAction
     * @return response entity containing a VideoDTO.
     */
    @Secured({"ROLE_ADMIN"})
    @PatchMapping("/publishing/{videoId}")
    public ResponseEntity<VideoDTO> videoPublishing(@PathVariable Long videoId, @Valid @RequestBody final String publishingAction) {
        return videoService.videoPublishing(videoId, publishingAction);
    }


    // COUNTERS

    /**
     * Counts all videos.
     * @return response entity containing a total number of videos.
     */
    @Secured({"ROLE_ADMIN"})
    @GetMapping(path = "/count/all")
    public ResponseEntity<Long> countAllVideos() { return videoService.countAllVideos(); }

    /**
     * Counts all imported (and not yet published or removed) videos.
     * @return response entity containing a total number of imported videos.
     */
    @Secured({"ROLE_ADMIN"})
    @GetMapping(path = "/count/imported")
    public ResponseEntity<Long> countImported() { return videoService.countImportedVideos(); }

    /**
     * Counts all published videos.
     * @return response entity containing a total number of published videos.
     */
    @Secured({"ROLE_ADMIN"})
    @GetMapping(path = "/count/published")
    public ResponseEntity<Long> countPublished() { return videoService.countPublishedVideos(); }

    /**
     * Counts all removed videos.
     * @return response entity containing a total number of removed videos.
     */
    @Secured({"ROLE_ADMIN"})
    @GetMapping(path = "/count/removed")
    public ResponseEntity<Long> countRemoved() { return videoService.countRemovedVideos(); }

}
