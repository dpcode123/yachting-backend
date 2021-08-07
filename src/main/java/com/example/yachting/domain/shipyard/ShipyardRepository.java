package com.example.yachting.domain.shipyard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Shipyard repository.
 * @author dp
 */
@Repository
public interface ShipyardRepository extends JpaRepository<Shipyard, Long> {
    /**
     * Counts all shipyards.
     * @return total number of shipyards
     */
    Long countAllBy();

    /**
     * Gets a shipyard by id.
     * @param id
     * @return shipyard wrapped in an Optional
     */
    Optional<Shipyard> findById(Long id);

    /**
     * Deletes an existing shipyard.
     * @param id
     * @return number of deleted shipyards (0 or 1)
     */
    Long removeById(Long id);

}
