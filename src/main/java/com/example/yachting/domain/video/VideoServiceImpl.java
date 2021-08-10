package com.example.yachting.domain.video;

import com.example.yachting.domain.common.ActivationAction;
import com.example.yachting.domain.common.EntityStatus;
import com.example.yachting.domain.common.PublishingAction;
import com.example.yachting.domain.video.dto.VideoDTO;
import com.example.yachting.domain.video.dto.VideoWithRelatedVideosDTO;
import com.example.yachting.domain.video.page.VideosPageAdmin;
import com.example.yachting.domain.video.page.VideosPagePublic;
import com.example.yachting.domain.yacht.Yacht;
import com.example.yachting.domain.yacht.YachtRepository;
import com.example.yachting.exception.exceptions.ResourceNotFoundException;
import com.example.yachting.exception.exceptions.TransactionFailedException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Video service implementation.
 * @author dp
 */
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final YachtRepository yachtRepository;
    private final ModelMapper modelMapper;

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException
     */
    @Override
    @Transactional
    @Cacheable(cacheNames = "videodetails", value="videodetails", key="#videoId")
    public VideoWithRelatedVideosDTO getVideoWithRelatedVideosCacheable(Long videoId) {

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found."));

        List<VideoDTO> relatedVideosSameYacht = new ArrayList<>();
        List<VideoDTO> relatedVideosSameShipyard = new ArrayList<>();

        if(video.getYacht() != null) {
            Long yachtId = video.getYacht().getId();
            Long shipyardId = video.getYacht().getShipyard().getId();
            relatedVideosSameYacht = getRelatedVideosByYacht(yachtId, videoId);
            relatedVideosSameShipyard = getRelatedVideosByShipyard(shipyardId, videoId);
        }

        VideoWithRelatedVideosDTO videoWithRelatedVideosDTO = new VideoWithRelatedVideosDTO(
                mapVideoToDTO(video),
                relatedVideosSameYacht,
                relatedVideosSameShipyard);

        return videoWithRelatedVideosDTO;
    }


    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException
     */
    @Override
    public List<VideoDTO> findAllVideos() {
        List<Video> videos =  videoRepository.findAll();
        if (videos.isEmpty()) {
            throw new ResourceNotFoundException("No videos found.");
        }

        List<VideoDTO> videoDTOS = mapVideosToDTOS(videos);
        return videoDTOS;
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException
     */
    @Override
    public List<VideoDTO> findImportedVideos() {
        List<Video> videos =  videoRepository.findAllByActiveTrueAndPublishedFalseOrderByImportedAtDesc();
        if (videos.isEmpty()) {
            throw new ResourceNotFoundException("No videos found.");
        }

        List<VideoDTO> videoDTOS = mapVideosToDTOS(videos);
        return videoDTOS;
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException
     */
    @Override
    public List<VideoDTO> findPublishedVideos() {
        List<Video> videos =  videoRepository.findAllByActiveTrueAndPublishedTrueOrderByPublishedUpdatedAtDesc();
        if (videos.isEmpty()) {
            throw new ResourceNotFoundException("No videos found.");
        }

        List<VideoDTO> videoDTOS = mapVideosToDTOS(videos);
        return videoDTOS;
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException
     */
    @Override
    public List<VideoDTO> findInactiveVideos() {
        List<Video> videos =  videoRepository.findAllByActiveFalseOrderByActiveUpdatedAtDesc();
        if (videos.isEmpty()) {
            throw new ResourceNotFoundException("No videos found.");
        }

        List<VideoDTO> videoDTOS = mapVideosToDTOS(videos);
        return videoDTOS;
    }

    /** {@inheritDoc} */
    @Override
    public Long countAllVideos() {
        Long count = videoRepository.countAllBy();
        return count;
    }

    /** {@inheritDoc} */
    @Override
    public Long countImportedVideos() {
        Long count = videoRepository.countAllByActiveTrueAndPublishedFalse();
        return count;
    }

    /** {@inheritDoc} */
    @Override
    public Long countPublishedVideos() {
        Long count = videoRepository.countAllByActiveTrueAndPublishedTrue();
        return count;
    }

    /** {@inheritDoc} */
    @Override
    public Long countRemovedVideos() {
        Long count = videoRepository.countAllByActiveFalse();
        return count;
    }

    /** {@inheritDoc} */
    @Override
    public VideoDTO findVideoById(Long videoId) {
        VideoDTO videoDTO = videoRepository.findById(videoId)
                .map(this::mapVideoToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found."));

        return videoDTO;
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException if video is not found
     * @throws ResourceNotFoundException if video's yacht is not found
     * @throws TransactionFailedException if video saving transaction failed
     */
    @Override
    @Transactional
    public VideoDTO editVideo(Long videoId, VideoCommand videoCommand) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found."));

        video.setTitle(videoCommand.getTitle());
        video.setYoutubeId(videoCommand.getYoutubeId());

        Yacht yacht = null;
        if (videoCommand.getYachtId() != null) {
            yacht = yachtRepository.findById(videoCommand.getYachtId())
                    .orElseThrow(() -> new ResourceNotFoundException("Video's yacht not found."));
        }
        video.setYacht(yacht);

        VideoDTO videoDTO = Optional.of(videoRepository.save(video))
                .map(this::mapVideoToDTO)
                .orElseThrow(() -> new TransactionFailedException("Video not edited."));

        return videoDTO;
    }

    /**
     * {@inheritDoc}
     * If video has yacht, get videos with the same yacht and same shipyard.
     * Otherwise, empty lists of related videos are added to DTO.
     * @throws ResourceNotFoundException if video is not found
     */
    @Override
    public VideoWithRelatedVideosDTO getVideoWithRelatedVideos(Long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found."));

        List<VideoDTO> relatedVideosSameYacht = new ArrayList<>();
        List<VideoDTO> relatedVideosSameShipyard = new ArrayList<>();

        if(video.getYacht() != null) {
            Long yachtId = video.getYacht().getId();
            Long shipyardId = video.getYacht().getShipyard().getId();
            relatedVideosSameYacht = getRelatedVideosByYacht(yachtId, videoId);
            relatedVideosSameShipyard = getRelatedVideosByShipyard(shipyardId, videoId);
        }

        VideoWithRelatedVideosDTO videoWithRelatedVideosDTO = new VideoWithRelatedVideosDTO(
                mapVideoToDTO(video),
                relatedVideosSameYacht,
                relatedVideosSameShipyard);
        return videoWithRelatedVideosDTO;
    }

    /** {@inheritDoc} */
    @Override
    public Page<VideoDTO> getPaginatedVideosPublic(VideosPagePublic videosPagePublic) {
        int pageNumber = Integer.parseInt(videosPagePublic.getPageNumber());
        int pageSize = videosPagePublic.getPageSize();

        Sort sort = Sort.by(videosPagePublic.getSortDirection(), videosPagePublic.getSortBy());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<VideoDTO> page = videoRepository.findAllByActiveTrueAndPublishedTrueOrderByPublishedUpdatedAtDesc(pageable)
                .map(this::mapVideoToDTO);

        return page;
    }

    /** {@inheritDoc} */
    @Override
    public Page<VideoDTO> getPaginatedVideosAdminFilteredByEntityStatus(VideosPageAdmin videosPageAdmin, EntityStatus entityStatus) {
        int pageNumber = Integer.parseInt(videosPageAdmin.getPageNumber());
        int pageSize = videosPageAdmin.getPageSize();

        Sort sort = Sort.by(videosPageAdmin.getSortDirection(), videosPageAdmin.getSortBy());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Video> videosPage = switch (entityStatus) {
            case IMPORTED -> videoRepository.findAllByActiveTrueAndPublishedFalseOrderByImportedAtDesc(pageable);
            case PUBLISHED -> videoRepository.findAllByActiveTrueAndPublishedTrueOrderByPublishedUpdatedAtDesc(pageable);
            case REMOVED -> videoRepository.findAllByActiveFalseOrderByActiveUpdatedAtDesc(pageable);
        };

        Page<VideoDTO> videoDTOSPage = videosPage.map(this::mapVideoToDTO);
        return videoDTOSPage;
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException
     */
    @Override
    public VideoDTO findVideoByYoutubeId(String youtubeId) {
        VideoDTO videoDTO = videoRepository.findByYoutubeId(youtubeId)
                .map(this::mapVideoToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found."));

        return videoDTO;
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException
     */
    @Override
    public List<VideoDTO> findVideosByYachtId(Long yachtId) {
        List<VideoDTO> videoDTOS = getVideosByYacht(yachtId);
        if(videoDTOS.isEmpty()) {
            throw new ResourceNotFoundException("No videos found.");
        }

        return videoDTOS;
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException
     */
    @Override
    public List<VideoDTO> findVideosByYachtShipyardId(Long shipyardId) {
        List<VideoDTO> videoDTOS = getVideosByShipyard(shipyardId);
        if(videoDTOS.isEmpty()) {
            throw new ResourceNotFoundException("No videos found.");
        }

        return videoDTOS;
    }


    // UPDATING VIDEO STATUS

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException
     */
    @Override
    @Transactional
    public VideoDTO videoActivation(Long videoId, String activationAction) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found."));

        Boolean videoActive = activationAction.equalsIgnoreCase(String.valueOf(ActivationAction.ACTIVATE)) ? true : false;
        video.setActive(videoActive);
        video.setActiveUpdatedAt(LocalDateTime.now());

        Video updatedVideo = videoRepository.save(video);
        return mapVideoToDTO(updatedVideo);
    }

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException
     */
    @Override
    @Transactional
    public VideoDTO videoPublishing(Long videoId, String publishingAction) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video not found."));

        Boolean videoPublished = publishingAction.equalsIgnoreCase(String.valueOf(PublishingAction.PUBLISH)) ? true : false;
        video.setPublished(videoPublished);
        video.setPublishedUpdatedAt(LocalDateTime.now());

        Video updatedVideo = videoRepository.save(video);
        return mapVideoToDTO(updatedVideo);
    }


    // VIDEOS BY YACHT

    /**
     * Gets videos by yacht.
     * @param yachtId
     * @return list of VideoDTOs
     */
    private List<VideoDTO> getVideosByYacht(Long yachtId) {
        List<Video> videos = videoRepository.findByYachtId(yachtId);
        return videos.stream()
                .map(this::mapVideoToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Gets related videos (all videos except this) by yacht.
     * @param yachtId
     * @return list of VideoDTOs
     */
    private List<VideoDTO> getRelatedVideosByYacht(Long yachtId, Long videoId) {
        List<Video> videos = videoRepository.findTop8ByYachtIdAndIdNot(yachtId, videoId);
        return videos.stream()
                .map(this::mapVideoToDTO)
                .collect(Collectors.toList());
    }


    // VIDEOS BY SHIPYARD

    /**
     * Gets videos by shipyard.
     * @param shipyardId
     * @return list of VideoDTOs
     */
    private List<VideoDTO> getVideosByShipyard(Long shipyardId) {
        List<Video> videos = videoRepository.findByYachtShipyardId(shipyardId);
        return videos.stream()
                .map(this::mapVideoToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Gets related videos (all videos except this) by shipyard.
     * @param shipyardId
     * @param videoId
     * @return list of VideoDTOs
     */
    private List<VideoDTO> getRelatedVideosByShipyard(Long shipyardId, Long videoId) {
        List<Video> videos = videoRepository.findTop12ByYachtShipyardIdAndIdNot(shipyardId, videoId);
        return videos.stream()
                .map(this::mapVideoToDTO)
                .collect(Collectors.toList());
    }


    // MAPPING

    /**
     * Maps video object to DTO.
     * @param video
     * @return
     */
    private VideoDTO mapVideoToDTO(Video video) {
        return modelMapper.map(video, VideoDTO.class);
    }

    /**
     * Maps list of video objects to list of DTOs.
     * @param videos
     * @return
     */
    private List<VideoDTO> mapVideosToDTOS(List<Video> videos) {
        return videos.stream()
                .map(this::mapVideoToDTO)
                .collect(Collectors.toList());
    }

}
