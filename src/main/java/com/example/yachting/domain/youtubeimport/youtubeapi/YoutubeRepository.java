package com.example.yachting.domain.youtubeimport.youtubeapi;

import com.example.yachting.domain.youtubeimport.youtubeapi.model.SearchResponseObjectListOfVideos;
import com.example.yachting.domain.youtubeimport.youtubeapi.model.SearchResponseObjectSingleVideoDetails;
import com.example.yachting.domain.youtubeimport.youtubeapi.model.YoutubeVideoFromDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.MessageFormat;
import java.util.List;

@Repository
public class YoutubeRepository {

    // Single video by youtube id:
    // https://youtube.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&id=[YOUTUBE_VIDEO_ID]&key=[YOUR_API_KEY]
    // Multiple videos by keyword:
    // https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=[MAX_RESULTS]&q=[KEYWORD]&type=video&videoEmbeddable=true&key=[YOUR_API_KEY]

    @Value("${youtube.api-key}")
    private String YOUTUBE_API_KEY;

    private final WebClient.Builder webClientBuilder;

    public YoutubeRepository(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public SearchResponseObjectListOfVideos searchVideosByKeyword(Integer maxResults, String keyword, String pageToken) {
        // first page does not have pageToken parameter
        if(pageToken.equals("first")) {
            pageToken = "";
        }

        String requestUri = MessageFormat.format(
                "https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults={0}&q={1}&type=video&videoEmbeddable=true&key={2}&pageToken={3}",
                maxResults, keyword, YOUTUBE_API_KEY, pageToken);

        SearchResponseObjectListOfVideos searchResponseObjectListOfVideos = webClientBuilder
                .build()
                .get()
                .uri(requestUri)
                .retrieve()
                .bodyToMono(SearchResponseObjectListOfVideos.class)
                .block();

        return searchResponseObjectListOfVideos;
    }

    public List<YoutubeVideoFromDetails> getVideoById(String videoId) {
        String requestUri = MessageFormat.format(
                "https://youtube.googleapis.com/youtube/v3/videos?part=snippet&contentDetails&statistics&id={0}&key={1}",
                videoId, YOUTUBE_API_KEY);

        SearchResponseObjectSingleVideoDetails searchResponseObject = webClientBuilder
                .build()
                .get()
                .uri(requestUri)
                .retrieve()
                .bodyToMono(SearchResponseObjectSingleVideoDetails.class)
                .block();

        return searchResponseObject.getJsonItems();
    }

}
