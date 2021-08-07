package com.example.yachting.domain.yacht;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Yacht repository.
 * @author dp
 */
@Repository
public interface YachtRepository extends JpaRepository<Yacht, Long> {
    /**
     * Counts all yachts.
     * @return total number of yachts
     */
    Long countAllBy();

    /**
     * Get a yacht by id.
     * @param id
     * @return yacht wrapped in an Optional
     */
    Optional<Yacht> findById(Long id);

    /**
     * Deletes an existing yacht.
     * @param id
     * @return number of deleted yachts (0 or 1)
     */
    Long removeById(Long id);

}
