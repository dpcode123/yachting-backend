package com.example.yachting.domain.youtubeimport.youtubeapi.testing;

import com.example.yachting.domain.youtubeimport.youtubeapi.YoutubeRepository;
import com.example.yachting.domain.youtubeimport.youtubeapi.model.YoutubeVideoFromDetails;
import com.example.yachting.domain.youtubeimport.youtubeapi.model.YoutubeVideoFromList;
import com.example.yachting.exception.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YoutubeApiServiceImpl implements YoutubeApiService {

    private final YoutubeRepository youtubeRepository;

    public YoutubeApiServiceImpl(YoutubeRepository youtubeRepository) {
        this.youtubeRepository = youtubeRepository;
    }

    @Override
    public ResponseEntity<List<YoutubeVideoFromDetails>> getVideoById(String videoId) {
        List<YoutubeVideoFromDetails> videos = youtubeRepository.getVideoById(videoId);
        if(videos.isEmpty()) {
            throw new ResourceNotFoundException("Video not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(videos);
    }

    @Override
    public ResponseEntity<List<YoutubeVideoFromList>> searchVideosByKeyword(Integer maxResults, String keyword, String pageToken) {
        List<YoutubeVideoFromList> videos = youtubeRepository.searchVideosByKeyword(maxResults, keyword, pageToken).getJsonItems();
        if(videos.isEmpty()) {
            throw new ResourceNotFoundException("No videos found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(videos);
    }

}
