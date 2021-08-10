package com.example.yachting.domain.video;

import com.example.yachting.domain.common.EntityStatus;
import com.example.yachting.domain.video.dto.VideoDTO;
import com.example.yachting.domain.video.dto.VideoWithRelatedVideosDTO;
import com.example.yachting.domain.video.page.VideosPageAdmin;
import com.example.yachting.domain.video.page.VideosPagePublic;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Video service.
 * @author dp
 */
public interface VideoService {

    /**
     * Gets a single CACHEABLE video with related videos.
     * @param id
     * @return VideoWithRelatedVideosDTO
     */
    VideoWithRelatedVideosDTO getVideoWithRelatedVideosCacheable(Long id);

    /**
     * Gets a single video.
     * @param id
     * @return VideoDTO.
     */
    VideoDTO findVideoById(Long id);

    /**
     * Gets all videos.
     * @return list of VideoDTOs.
     */
    List<VideoDTO> findAllVideos();

    /**
     * Gets all imported (and not yet published or removed) videos.
     * @return list of VideoDTOs.
     */
    List<VideoDTO> findImportedVideos();

    /**
     * Gets all published videos.
     * @return list of VideoDTOs.
     */
    List<VideoDTO> findPublishedVideos();

    /**
     * Gets all inactive(removed) videos.
     * @return list of VideoDTOs.
     */
    List<VideoDTO> findInactiveVideos();

    /**
     * Updates video's details.
     * @param videoId
     * @param videoCommand
     * @return VideoDTO.
     */
    VideoDTO editVideo(Long videoId, VideoCommand videoCommand);

    /**
     * Updates video's activation status.
     * @param videoId
     * @param activationAction
     * @return VideoDTO.
     */
    VideoDTO videoActivation(Long videoId, String activationAction);

    /**
     * Updates video's publishing status.
     * @param videoId
     * @param publishingAction
     * @return VideoDTO.
     */
    VideoDTO videoPublishing(Long videoId, String publishingAction);

    /**
     * Gets a single video with related videos.
     * @param id
     * @return VideoWithRelatedVideosDTO.
     */
    VideoWithRelatedVideosDTO getVideoWithRelatedVideos(Long id);

    /**
     * Gets single video by youtube id.
     * @param youtubeId
     * @return VideoDTO.
     */
    VideoDTO findVideoByYoutubeId(String youtubeId);

    /**
     * Gets paginated videos for displaying to user on a PUBLIC page.
     * @param videosPagePublic
     * @return response entity containing a page of VideoDTOs.
     */
    Page<VideoDTO> getPaginatedVideosPublic(VideosPagePublic videosPagePublic);

    /**
     * Gets CACHEABLE paginated videos for displaying to user on a PUBLIC page.
     * @param videosPagePublic
     * @return response entity containing a page of VideoDTOs.
     */
    Page<VideoDTO> getPaginatedVideosPublicCacheable(VideosPagePublic videosPagePublic);


    /**
     * Gets paginated videos filtered by entity status.
     * For displaying videos in ADMIN interface.
     * @param videosPageAdmin
     * @param entityStatus
     * @return page of VideoDTOs.
     */
    Page<VideoDTO> getPaginatedVideosAdminFilteredByEntityStatus(VideosPageAdmin videosPageAdmin, EntityStatus entityStatus);

    /**
     * Gets videos by yacht.
     * @param yachtId
     * @return list of VideoDTOs.
     */
    List<VideoDTO> findVideosByYachtId(Long yachtId);

    /**
     * Gets videos by yacht's shipyard.
     * @param shipyardId
     * @return list of VideoDTOs.
     */
    List<VideoDTO> findVideosByYachtShipyardId(Long shipyardId);


    // COUNTERS
    // #########################################################################

    /**
     * Counts all videos.
     * @return total number of videos.
     */
    Long countAllVideos();

    /**
     * Counts all imported (and not yet published or removed) videos.
     * @return total number of imported videos.
     */
    Long countImportedVideos();

    /**
     * Counts all published videos.
     * @return total number of published videos.
     */
    Long countPublishedVideos();

    /**
     * Counts all removed videos.
     * @return total number of removed videos.
     */
    Long countRemovedVideos();

}
