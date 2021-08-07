package com.example.yachting.domain.video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Video repository.
 * @author dp
 */
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    /**
     * Counts all videos.
     * @return total number of all videos.
     */
    Long countAllBy();

    /**
     * Gets video by youtube id.
     * @param youtubeId
     * @return video wrapped in an Optional
     */
    Optional<Video> findByYoutubeId(String youtubeId);

    /**
     * Gets videos by yacht.
     * @param yachtId
     * @return list of videos
     */
    List<Video> findByYachtId(Long yachtId);

    /**
     * Gets videos by yacht's shipyard.
     * @param shipyardId
     * @return list of videos
     */
    List<Video> findByYachtShipyardId(Long shipyardId);


    // RELATED VIDEOS BY YACHT/SHIPYARD (all videos except this video)

    /**
     * Gets related videos by yacht.
     * @param yachtId
     * @param id
     * @return list of videos
     */
    List<Video> findTop8ByYachtIdAndIdNot(Long yachtId, Long id);

    /**
     * Gets related videos by shipyard.
     * @param shipyardId
     * @param id
     * @return list of videos
     */
    List<Video> findTop12ByYachtShipyardIdAndIdNot(Long shipyardId, Long id);


    // VIDEOS FILTERED BY STATUS: IMPORTED VIDEOS

    /**
     * Counts all imported (and not yet published or removed) videos.
     * @return total number of imported videos
     */
    Long countAllByActiveTrueAndPublishedFalse();

    /**
     * Gets all imported (and not yet published or removed) videos.
     * @return list of imported videos
     */
    List<Video> findAllByActiveTrueAndPublishedFalseOrderByImportedAtDesc();

    /**
     * Gets paginated imported (and not yet published or removed) videos.
     * @param pageable
     * @return page of imported videos
     */
    Page<Video> findAllByActiveTrueAndPublishedFalseOrderByImportedAtDesc(Pageable pageable);


    // VIDEOS FILTERED BY STATUS: PUBLISHED VIDEOS

    /**
     * Counts all published videos.
     * @return total number of published videos
     */
    Long countAllByActiveTrueAndPublishedTrue();

    /**
     * Gets all published videos.
     * @return list of published videos
     */
    List<Video> findAllByActiveTrueAndPublishedTrueOrderByPublishedUpdatedAtDesc();

    /**
     * Gets paginated published videos.
     * @param pageable
     * @return page of published videos
     */
    Page<Video> findAllByActiveTrueAndPublishedTrueOrderByPublishedUpdatedAtDesc(Pageable pageable);


    // VIDEOS FILTERED BY STATUS: REMOVED VIDEOS

    /**
     * Counts all removed videos.
     * @return total number of removed videos
     */
    Long countAllByActiveFalse();

    /**
     * Gets all removed videos.
     * @return list of removed videos
     */
    List<Video> findAllByActiveFalseOrderByActiveUpdatedAtDesc();

    /**
     * Gets paginated removed videos.
     * @param pageable
     * @return page of removed videos
     */
    Page<Video> findAllByActiveFalseOrderByActiveUpdatedAtDesc(Pageable pageable);

}
