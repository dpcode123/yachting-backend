package com.example.yachting.domain.shipyard;

import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Shipyard service.
 * @author dp
 */
public interface ShipyardService {
    /**
     * Gets all shipyards.
     * @return list of ShipyardDTOs
     */
    List<ShipyardDTO> findAllShipyards();

    /**
     * Counts all shipyards.
     * @return total number of shipyards
     */
    Long countAllShipyards();

    /**
     * Gets a shipyard by id.
     * @param shipyardId
     * @return ShipyardDTO
     */
    ShipyardDTO findShipyardById(Long shipyardId);

    /**
     * Adds a new shipyard.
     * @param shipyardCommand
     * @return ShipyardDTO
     */
    ShipyardDTO addShipyard(ShipyardCommand shipyardCommand);

    /**
     * Edits an existing shipyard.
     * @param shipyardId
     * @param shipyardCommand
     * @return ShipyardDTO
     */
    ShipyardDTO editShipyard(Long shipyardId, ShipyardCommand shipyardCommand);

    /**
     * Deletes an existing shipyard.
     * @param shipyardId
     * @return number of deleted shipyards (0 or 1)
     */
    Long deleteShipyard(Long shipyardId);

}
