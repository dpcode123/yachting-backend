package com.example.yachting.domain.youtubeimport.youtubeapi.testing;

import com.example.yachting.domain.youtubeimport.youtubeapi.model.YoutubeVideoFromDetails;
import com.example.yachting.domain.youtubeimport.youtubeapi.model.YoutubeVideoFromList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"${client.origin}"})
@RequestMapping(path="youtube-api-test")
public class YoutubeApiController {

    private final YoutubeApiService youtubeApiService;

    public YoutubeApiController(YoutubeApiService youtubeApiService) {
        this.youtubeApiService = youtubeApiService;
    }

    @GetMapping(path = "/single/{videoId}")
    public ResponseEntity<List<YoutubeVideoFromDetails>> getSingleVideo(@PathVariable String videoId) {
        return youtubeApiService.getVideoById(videoId);
    }

    @GetMapping(path = "/multi/{maxResults}/{keyword}/{pageToken}")
    public ResponseEntity<List<YoutubeVideoFromList>> getMultipleVideos(@PathVariable Integer maxResults, @PathVariable String keyword, @PathVariable String pageToken) {
        return youtubeApiService.searchVideosByKeyword(maxResults, keyword, pageToken);
    }
}
