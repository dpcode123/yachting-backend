package com.example.yachting.domain.staticpage;

/**
 * Static page service.
 * @author dp
 */
public interface StaticPageService {
    /**
     * Gets a static page by name.
     * @param name Name of a page
     * @return Static page entity
     */
    StaticPage getStaticPage(String name);
}
