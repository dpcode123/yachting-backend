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
     * @return list of YachtDTOs
     */
    List<YachtDTO> findAllYachts();

    /**
     * Counts all yachts.
     * @return total number of yachts
     */
    Long countAllYachts();

    /**
     * Gets a yacht by id.
     * @param yachtId
     * @return YachtDTO
     */
    YachtDTO findYachtById(Long yachtId);

    /**
     * Adds a new yacht.
     * @param yachtCommand
     * @return YachtDTO
     */
    YachtDTO addYacht(YachtCommand yachtCommand);

    /**
     * Edits an existing yacht.
     * @param yachtId
     * @param yachtCommand
     * @return YachtDTO
     */
    YachtDTO editYacht(Long yachtId, YachtCommand yachtCommand);

    /**
     * Deletes an existing yacht.
     * @param yachtId
     * @return number of deleted yachts (0 or 1)
     */
    Long deleteYacht(Long yachtId);

}
