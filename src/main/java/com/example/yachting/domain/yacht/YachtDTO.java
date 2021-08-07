package com.example.yachting.domain.yacht;

import com.example.yachting.domain.shipyard.Shipyard;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for Yacht class.
 * @author dp
 */
@Data
public class YachtDTO {
    private Long id;
    private Shipyard shipyard;
    private String modelName;
    private Integer lengthOverallMeters;
    private Integer beamMeters;
    private String imageYoutubeId;
}
