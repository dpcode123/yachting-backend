package com.example.yachting.domain.youtubeimport.youtubeapi.testing;

import com.example.yachting.domain.youtubeimport.youtubeapi.model.YoutubeVideoFromDetails;
import com.example.yachting.domain.youtubeimport.youtubeapi.model.YoutubeVideoFromList;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface YoutubeApiService {
    ResponseEntity<List<YoutubeVideoFromDetails>> getVideoById(String videoId);
    ResponseEntity<List<YoutubeVideoFromList>> searchVideosByKeyword(Integer maxResults, String keyword, String pageToken);
}
