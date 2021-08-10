package com.example.yachting.domain.staticpage;

import com.example.yachting.exception.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Static page service implementation.
 * @author dp
 */
@Service
@RequiredArgsConstructor
public class StaticPageServiceImpl implements StaticPageService {

    private final StaticPageRepository staticPageRepository;

    /**
     * {@inheritDoc}
     * @throws ResourceNotFoundException if static page is not found
     */
    @Override
    public StaticPage getStaticPage(String name) {
        StaticPage staticPage = staticPageRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Static page not found."));
        return staticPage;
    }
}
