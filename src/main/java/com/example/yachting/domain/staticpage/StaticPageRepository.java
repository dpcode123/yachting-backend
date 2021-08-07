package com.example.yachting.domain.staticpage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Static page repository.
 * @author dp
 */
public interface StaticPageRepository extends JpaRepository<StaticPage, Long> {
    /**
     * Gets a static page by name.
     * @param name Name of a page
     * @return Static page wrapped in an Optional
     */
    Optional<StaticPage> findByName(String name);
}
