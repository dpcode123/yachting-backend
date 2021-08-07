package com.example.yachting.domain.staticpage;

import com.example.yachting.exception.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Static page service implementation.
 * @author dp
 */
@Service
public class StaticPageServiceImpl implements StaticPageService {

    private final StaticPageRepository staticPageRepository;

    public StaticPageServiceImpl(StaticPageRepository staticPageRepository) {
        this.staticPageRepository = staticPageRepository;
    }

    /**
     * Gets a static page by name.
     * @param name Name of a page
     * @return Response entity containing a static page
     */
    @Override
    public ResponseEntity<StaticPage> getStaticPage(String name) {
        StaticPage staticPage = staticPageRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Static page not found."));
        return ResponseEntity.status(HttpStatus.OK).body(staticPage);
    }
}
