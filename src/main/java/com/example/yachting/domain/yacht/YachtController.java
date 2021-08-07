package com.example.yachting.domain.yacht;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Yacht controller.
 * @author dp
 */
@RestController
@CrossOrigin(origins = {"${client.origin}"})
@RequestMapping(path="api/yacht")
public class YachtController {

    private final YachtService yachtService;

    public YachtController(YachtService yachtService) {
        this.yachtService = yachtService;
    }


    // PUBLIC ENDPOINTS
    // #########################################################################

    /**
     * Gets all yachts.
     * @return response entity containing a list of YachtDTOs
     */
    @GetMapping(path = "/public")
    public ResponseEntity<List<YachtDTO>> findAll() {
        return yachtService.findAllYachts();
    }

    /**
     * Gets a yacht by id.
     * @param yachtId
     * @return response entity containing a YachtDTO
     */
    @GetMapping(path = "/public/{yachtId}")
    public ResponseEntity<YachtDTO> findYachtById(@PathVariable Long yachtId) {
        return yachtService.findYachtById(yachtId);
    }


    // ADMIN ENDPOINTS
    // #########################################################################

    /**
     * Adds a new yacht.
     * @param yachtCommand
     * @return response entity containing a YachtDTO
     */
    @Secured({"ROLE_ADMIN"})
    @PostMapping("/admin/add")
    public ResponseEntity<YachtDTO> addYacht(@Valid @RequestBody final YachtCommand yachtCommand) {
        return yachtService.addYacht(yachtCommand);
    }

    /**
     * Edits an existing yacht.
     * @param yachtId
     * @param yachtCommand
     * @return response entity containing a YachtDTO
     */
    @Secured({"ROLE_ADMIN"})
    @PutMapping("/admin/edit/{yachtId}")
    public ResponseEntity<YachtDTO> editYacht(@PathVariable Long yachtId, @Valid @RequestBody final YachtCommand yachtCommand) {
        return yachtService.editYacht(yachtId, yachtCommand);
    }

    /**
     * Deletes an existing yacht.
     * @param yachtId
     * @return response entity containing a YachtDTO
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/admin/delete/{yachtId}")
    public ResponseEntity<Long> deleteYachtById(@PathVariable Long yachtId) {
        return yachtService.deleteYacht(yachtId);
    }

    /**
     * Counts all yachts.
     * @return response entity containing a total number of yachts
     */
    @Secured({"ROLE_ADMIN"})
    @GetMapping(path = "/admin/count")
    public ResponseEntity<Long> countAllYachts() { return yachtService.countAllYachts(); }

}
