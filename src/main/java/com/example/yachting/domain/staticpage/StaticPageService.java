package com.example.yachting.domain.staticpage;

import org.springframework.http.ResponseEntity;

/**
 * Static page service.
 * @author dp
 */
public interface StaticPageService {
    /**
     * Gets a static page by name.
     * @param name Name of a page
     * @return Response entity containing a static page
     */
    ResponseEntity<StaticPage> getStaticPage(String name);
}
