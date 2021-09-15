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
import com.example.yachting.exception.exceptions.NoContentFoundException;
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
import org.springframework.transaction.annotation.Transactional;

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


    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "publicvideospage", value = "publicvideospage", key = "#videosPagePublic")
    public Page<VideoDTO> getCacheablePaginatedPublicVideosPage(VideosPagePublic videosPagePublic) {
        int pageNumber = Integer.parseInt(videosPagePublic.getPageNumber());
        int pageSize = videosPagePublic.getPageSize();

        Sort sort = Sort.by(videosPagePublic.getSortDirection(), videosPagePublic.getSortBy());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<VideoDTO> page = videoRepository.findAllByActiveTrueAndPublishedTrueOrderByPublishedUpdatedAtDesc(pageable)
                .map(this::mapVideoToDTO);

        return page;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "videodetails", value="videodetails", key="#videoId")
    public VideoWithRelatedVideosDTO getCacheableVideoWithRelatedVideos(Long videoId) {
        return getVideoWithRelatedVideosDTO(videoId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public VideoWithRelatedVideosDTO getVideoWithRelatedVideos(Long videoId) {
        return getVideoWithRelatedVideosDTO(videoId);
    }

    /**
     * @param videoId
     * @throws NoContentFoundException if video is not found
     * If video has a yacht, get videos with the same yacht and the same shipyard.
     * Otherwise, empty lists of related videos are added to DTO.
     * @return video with videos related by yacht and shipyard
     */
    private VideoWithRelatedVideosDTO getVideoWithRelatedVideosDTO(Long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new NoContentFoundException("Video not found."));

        List<VideoDTO> relatedVideosSameYacht = new ArrayList<>();
        List<VideoDTO> relatedVideosSameShipyard = new ArrayList<>();

        if(video.getYacht() != null) {
            Long yachtId = video.getYacht().getId();
            Long shipyardId = video.getYacht().getShipyard().getId();
            relatedVideosSameYacht = getRelatedVideosByYacht(yachtId, videoId);
            relatedVideosSameShipyard = getRelatedVideosByShipyard(shipyardId, videoId);
        }

        return new VideoWithRelatedVideosDTO(
                mapVideoToDTO(video),
                relatedVideosSameYacht,
                relatedVideosSameShipyard);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<VideoDTO> findAllVideos() {
        return videoRepository.findAll()
                .stream()
                .map(this::mapVideoToDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<VideoDTO> findImportedVideos() {
        return videoRepository.findAllByActiveTrueAndPublishedFalseOrderByImportedAtDesc()
                .stream()
                .map(this::mapVideoToDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<VideoDTO> findPublishedVideos() {
        return videoRepository.findAllByActiveTrueAndPublishedTrueOrderByPublishedUpdatedAtDesc()
                .stream()
                .map(this::mapVideoToDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<VideoDTO> findInactiveVideos() {
        return videoRepository.findAllByActiveFalseOrderByActiveUpdatedAtDesc().stream()
                .map(this::mapVideoToDTO)
                .collect(Collectors.toList());
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Long countAllVideos() {
        return videoRepository.countAllBy();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Long countImportedVideos() {
        return videoRepository.countAllByActiveTrueAndPublishedFalse();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Long countPublishedVideos() {
        return videoRepository.countAllByActiveTrueAndPublishedTrue();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Long countRemovedVideos() {
        return videoRepository.countAllByActiveFalse();
    }

    /** {@inheritDoc}
     * @throws NoContentFoundException if video is not found
     */
    @Override
    @Transactional(readOnly = true)
    public VideoDTO findVideoById(Long videoId) {
        VideoDTO videoDTO = videoRepository.findById(videoId)
                .map(this::mapVideoToDTO)
                .orElseThrow(() -> new NoContentFoundException("Video not found."));

        return videoDTO;
    }

    /**
     * {@inheritDoc}
     * @throws NoContentFoundException if video is not found
     * @throws NoContentFoundException if video's yacht is not found
     * @throws TransactionFailedException if video saving transaction failed
     */
    @Override
    @Transactional
    public VideoDTO editVideo(Long videoId, VideoCommand videoCommand) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new NoContentFoundException("Video not found."));

        video.setTitle(videoCommand.getTitle());
        video.setYoutubeId(videoCommand.getYoutubeId());

        Yacht yacht = null;
        if (videoCommand.getYachtId() != null) {
            yacht = yachtRepository.findById(videoCommand.getYachtId())
                    .orElseThrow(() -> new NoContentFoundException("Video's yacht not found."));
        }
        video.setYacht(yacht);

        VideoDTO videoDTO = Optional.of(videoRepository.save(video))
                .map(this::mapVideoToDTO)
                .orElseThrow(() -> new TransactionFailedException("Video not edited."));

        return videoDTO;
    }








    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
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
     * @throws NoContentFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public VideoDTO findVideoByYoutubeId(String youtubeId) {
        VideoDTO videoDTO = videoRepository.findByYoutubeId(youtubeId)
                .map(this::mapVideoToDTO)
                .orElseThrow(() -> new NoContentFoundException("Video not found."));

        return videoDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<VideoDTO> findVideosByYachtId(Long yachtId) {
        return getVideosByYacht(yachtId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<VideoDTO> findVideosByYachtShipyardId(Long shipyardId) {
        return getVideosByShipyard(shipyardId);
    }


    // UPDATING VIDEO STATUS

    /**
     * {@inheritDoc}
     * @throws NoContentFoundException
     */
    @Override
    @Transactional
    public VideoDTO videoActivation(Long videoId, String activationAction) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new NoContentFoundException("Video not found."));

        Boolean videoActive = activationAction.equalsIgnoreCase(String.valueOf(ActivationAction.ACTIVATE)) ? true : false;
        video.setActive(videoActive);
        video.setActiveUpdatedAt(LocalDateTime.now());

        Video updatedVideo = videoRepository.save(video);
        return mapVideoToDTO(updatedVideo);
    }

    /**
     * {@inheritDoc}
     * @throws NoContentFoundException
     */
    @Override
    @Transactional
    public VideoDTO videoPublishing(Long videoId, String publishingAction) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new NoContentFoundException("Video not found."));

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
        return videoRepository.findByYachtId(yachtId)
                .stream()
                .map(this::mapVideoToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Gets related videos (all videos except this) by yacht.
     * @param yachtId
     * @return list of VideoDTOs
     */
    private List<VideoDTO> getRelatedVideosByYacht(Long yachtId, Long videoId) {
        return videoRepository.findTop8ByYachtIdAndIdNot(yachtId, videoId)
                .stream()
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
        return videoRepository.findByYachtShipyardId(shipyardId)
                .stream()
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
        return videoRepository.findTop12ByYachtShipyardIdAndIdNot(shipyardId, videoId)
                .stream()
                .map(this::mapVideoToDTO)
                .collect(Collectors.toList());
    }


    // MAPPING

    /**
     * Maps video object to DTO.
     * @param video
     * @return
     */
    private VideoDTO mapVideoToDTO(final Video video) {
        return modelMapper.map(video, VideoDTO.class);
    }

    /**
     * Maps list of video objects to list of DTOs.
     * @param videos
     * @return
     */
    private List<VideoDTO> mapVideosToDTOS(final List<Video> videos) {
        return videos.stream()
                .map(this::mapVideoToDTO)
                .collect(Collectors.toList());
    }

}
