package com.example.yachting.domain.shipyard;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Shipyard controller.
 * @author dp
 */
@RestController
@CrossOrigin(origins = {"${client.origin}"})
@RequestMapping(path = "api/shipyard")
@RequiredArgsConstructor
public class ShipyardController {

    private final ShipyardService shipyardService;

    // PUBLIC ENDPOINTS
    // #########################################################################

    /**
     * Gets all shipyards.
     * @return response entity containing a list of ShipyardDTOs
     */
    @GetMapping(path = "/public")
    public ResponseEntity<List<ShipyardDTO>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(shipyardService.findAllShipyards());
    }

    /**
     * Gets a shipyard by id.
     * @param shipyardId
     * @return response entity containing a ShipyardDTO
     */
    @GetMapping(path = "/public/{shipyardId}")
    public ResponseEntity<ShipyardDTO> findShipyardById(@PathVariable Long shipyardId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(shipyardService.findShipyardById(shipyardId));
    }


    // ADMIN ENDPOINTS
    // #########################################################################

    /**
     * Adds a new shipyard.
     * @param shipyardCommand
     * @return response entity containing a ShipyardDTO
     */
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/admin/add")
    public ResponseEntity<ShipyardDTO> addShipyard(@Valid @RequestBody final ShipyardCommand shipyardCommand) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(shipyardService.addShipyard(shipyardCommand));
    }

    /**
     * Edits an existing shipyard.
     * @param shipyardId
     * @param shipyardCommand
     * @return response entity containing a ShipyardDTO
     */
    @Secured({"ROLE_ADMIN"})
    @PutMapping("/admin/edit/{shipyardId}")
    public ResponseEntity<ShipyardDTO> editShipyard(@PathVariable Long shipyardId, @Valid @RequestBody final ShipyardCommand shipyardCommand) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(shipyardService.editShipyard(shipyardId, shipyardCommand));
    }

    /**
     * Deletes an existing shipyard.
     * @param shipyardId
     * @return response entity containing a number of deleted shipyards (0 or 1)
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/admin/delete/{shipyardId}")
    public ResponseEntity<Long> deleteShipyardById(@PathVariable Long shipyardId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(shipyardService.deleteShipyard(shipyardId));
    }

    /**
     * Counts all shipyards.
     * @return response entity containing a total number of shipyards
     */
    @Secured({"ROLE_ADMIN"})
    @GetMapping(path = "/admin/count")
    public ResponseEntity<Long> countAllShipyards() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(shipyardService.countAllShipyards());
    }

}
