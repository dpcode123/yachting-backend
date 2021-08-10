package com.example.yachting.domain.staticpage;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Static page controller.
 * @author dp
 */
@RestController
@CrossOrigin(origins = {"${client.origin}"})
@RequestMapping(path = "api/static-page")
@RequiredArgsConstructor
public class StaticPageController {

    private final StaticPageService staticPageService;

    /**
     * Gets a static page by name.
     * @param name Name of a page
     * @return Response entity containing a static page
     */
    @GetMapping("/public/{name}")
    public ResponseEntity<StaticPage> getStaticPage(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(staticPageService.getStaticPage(name));
    }


}
