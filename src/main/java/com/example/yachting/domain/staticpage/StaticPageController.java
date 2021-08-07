package com.example.yachting.domain.staticpage;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Static page controller.
 * @author dp
 */
@RestController
@CrossOrigin(origins = {"${client.origin}"})
@RequestMapping(path = "api/static-page")
public class StaticPageController {

    private final StaticPageService staticPageService;

    public StaticPageController(StaticPageService staticPageService) {
        this.staticPageService = staticPageService;
    }

    /**
     * Gets a static page by name.
     * @param name Name of a page
     * @return Response entity containing a static page
     */
    @GetMapping("/public/{name}")
    public ResponseEntity<StaticPage> getStaticPage(@PathVariable String name) {
        return staticPageService.getStaticPage(name);
    }


}
