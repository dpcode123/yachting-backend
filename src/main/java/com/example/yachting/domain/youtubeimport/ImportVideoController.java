package com.example.yachting.domain.youtubeimport;

import com.example.yachting.domain.youtubeimport.youtubeapi.model.ResultsOfImportingByKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Video import controller.
 * @author dp
 */
@RestController
@CrossOrigin(origins = {"${client.origin}"})
@RequestMapping(path="api/admin/youtube-import/videos")
@RequiredArgsConstructor
public class ImportVideoController {

    private final ImportVideoService importVideoService;

    /**
     * Searches for videos on youtube api by keyword and imports them to database.
     * @param importVideosByKeywordCommand
     * @return response entity containing results of importing by keyword
     */
    @Secured({"ROLE_ADMIN"})
    @PostMapping(path = "/by-keyword")
    public ResponseEntity<ResultsOfImportingByKeyword> importVideosByKeyword(@Valid @RequestBody final ImportVideosByKeywordCommand importVideosByKeywordCommand) {
        return importVideoService.importVideosByKeyword(importVideosByKeywordCommand);
    }

}
