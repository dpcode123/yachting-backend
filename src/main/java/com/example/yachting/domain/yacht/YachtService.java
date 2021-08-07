package com.example.yachting.domain.yacht;

import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Yacht service.
 * @author dp
 */
public interface YachtService {
    /**
     * Gets all yachts.
     * @return response entity containing a list of yachts
     */
    ResponseEntity<List<YachtDTO>> findAllYachts();

    /**
     * Counts all yachts.
     * @return response entity containing a total number of yachts
     */
    ResponseEntity<Long> countAllYachts();

    /**
     * Gets a yacht by id.
     * @param yachtId
     * @return response entity containing a YachtDTO
     */
    ResponseEntity<YachtDTO> findYachtById(Long yachtId);

    /**
     * Adds a new yacht.
     * @param yachtCommand
     * @return response entity containing a YachtDTO
     */
    ResponseEntity<YachtDTO> addYacht(YachtCommand yachtCommand);

    /**
     * Edits an existing yacht.
     * @param yachtId
     * @param yachtCommand
     * @return response entity containing a YachtDTO
     */
    ResponseEntity<YachtDTO> editYacht(Long yachtId, YachtCommand yachtCommand);

    /**
     * Deletes an existing yacht.
     * @param yachtId
     * @return response entity containing a number of deleted yachts (0 or 1)
     */
    ResponseEntity<Long> deleteYacht(Long yachtId);

}
