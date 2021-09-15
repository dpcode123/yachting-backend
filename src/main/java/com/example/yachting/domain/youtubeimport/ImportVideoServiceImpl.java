package com.example.yachting.domain.youtubeimport;

import com.example.yachting.domain.video.Video;
import com.example.yachting.domain.video.VideoRepository;
import com.example.yachting.domain.yacht.Yacht;
import com.example.yachting.domain.yacht.YachtRepository;
import com.example.yachting.domain.youtubeimport.youtubeapi.YoutubeRepository;
import com.example.yachting.domain.youtubeimport.youtubeapi.model.ResultsOfImportingByKeyword;
import com.example.yachting.domain.youtubeimport.youtubeapi.model.SearchResponseObjectListOfVideos;
import com.example.yachting.domain.youtubeimport.youtubeapi.model.YoutubeVideoFromList;
import com.example.yachting.exception.exceptions.NoContentFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Implementation of service for importing videos from youtube.
 * @author dp
 */
@Service
@RequiredArgsConstructor
public class ImportVideoServiceImpl implements ImportVideoService {

    private final YoutubeRepository youtubeRepository;
    private final VideoRepository videoRepository;
    private final YachtRepository yachtRepository;

    /**
     * {@inheritDoc}
     * Gets list of videos from youtube.
     * For each fetched video, checks if it's already in repo.
     * If not, then imports it to repo.
     * @throws NoContentFoundException if no videos are found
     */
    @Override
    public ResponseEntity<ResultsOfImportingByKeyword> importVideosByKeyword(ImportVideosByKeywordCommand importVideosByKeywordCommand) {
        // get list of videos from youtube
        SearchResponseObjectListOfVideos searchResponseObjectListOfVideos =
                youtubeRepository.searchVideosByKeyword(
                    importVideosByKeywordCommand.getMaxResults(),
                    importVideosByKeywordCommand.getKeyword(),
                    importVideosByKeywordCommand.getPageToken()
                );
        List<YoutubeVideoFromList> videosFetchedFromYoutube = searchResponseObjectListOfVideos.getJsonItems();

        if (videosFetchedFromYoutube.isEmpty()) {
            throw new NoContentFoundException("No videos fetched from youtube api.");
        }

        String nextPageToken = searchResponseObjectListOfVideos.getNextPageToken();
        Yacht yacht = yachtRepository.findById(importVideosByKeywordCommand.getYachtId()).orElse(null);
        AtomicReference<Integer> importedVideosCounter = new AtomicReference<>(0);
        Integer alreadyImportedFetchedVideosCounter = 0;

        videosFetchedFromYoutube.forEach(
                //  if video is not already in repository, import it
                youtubeVideo -> {
                    Boolean videoAlreadyExistInRepo = videoRepository.findByYoutubeId(youtubeVideo.getVideoId()).isPresent();
                    if (!videoAlreadyExistInRepo) {
                        Video video = new Video.VideoBuilder()
                                .setTitle(youtubeVideo.getTitle())
                                .setYoutubeId(youtubeVideo.getVideoId())
                                .setYoutubeChannelId(youtubeVideo.getChannelId())
                                .setYacht(yacht)
                                .setImportedAt(LocalDateTime.now())
                                .setYoutubePublishedAt(youtubeVideo.getPublishedAt())
                                .build();
                        videoRepository.save(video);
                        importedVideosCounter.getAndSet(importedVideosCounter.get() + 1);
                    }
                }
        );

        alreadyImportedFetchedVideosCounter = videosFetchedFromYoutube.size() - importedVideosCounter.get();

        ResultsOfImportingByKeyword results = new ResultsOfImportingByKeyword(
                videosFetchedFromYoutube,
                videosFetchedFromYoutube.size(),
                alreadyImportedFetchedVideosCounter,
                importedVideosCounter.get(),
                nextPageToken
        );

        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

}
