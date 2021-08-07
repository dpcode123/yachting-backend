package com.example.yachting.domain.youtubeimport;

import com.example.yachting.domain.youtubeimport.youtubeapi.model.ResultsOfImportingByKeyword;
import org.springframework.http.ResponseEntity;

/**
 * Service for importing videos from youtube.
 * @author dp
 */
public interface ImportVideoService {
    /**
     * Searches for videos on youtube api by keyword and imports them to database.
     * @param importVideosByKeywordCommand
     * @return response entity containing results of importing by keyword
     */
    ResponseEntity<ResultsOfImportingByKeyword> importVideosByKeyword(ImportVideosByKeywordCommand importVideosByKeywordCommand);
}
