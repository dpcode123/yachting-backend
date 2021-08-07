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
     * @return response entity containing a list of ShipyardDTOs
     */
    ResponseEntity<List<ShipyardDTO>> findAllShipyards();

    /**
     * Counts all shipyards.
     * @return response entity containing a total number of shipyards
     */
    ResponseEntity<Long> countAllShipyards();

    /**
     * Gets a shipyard by id.
     * @param shipyardId
     * @return response entity containing a ShipyardDTO
     */
    ResponseEntity<ShipyardDTO> findShipyardById(Long shipyardId);

    /**
     * Adds a new shipyard.
     * @param shipyardCommand
     * @return response entity containing a ShipyardDTO
     */
    ResponseEntity<ShipyardDTO> addShipyard(ShipyardCommand shipyardCommand);

    /**
     * Edits an existing shipyard.
     * @param shipyardId
     * @param shipyardCommand
     * @return response entity containing a ShipyardDTO
     */
    ResponseEntity<ShipyardDTO> editShipyard(Long shipyardId, ShipyardCommand shipyardCommand);

    /**
     * Deletes an existing shipyard.
     * @param shipyardId
     * @return response entity containing a number of deleted shipyards (0 or 1)
     */
    ResponseEntity<Long> deleteShipyard(Long shipyardId);

}
