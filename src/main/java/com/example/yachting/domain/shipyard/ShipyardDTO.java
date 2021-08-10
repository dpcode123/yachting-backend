package com.example.yachting.domain.shipyard;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for Shipyard class.
 * @author dp
 */
@Data
@AllArgsConstructor
public class ShipyardDTO {
        private Long id;
        private String name;
        private String country;
        private Integer yearFounded;
        private String websiteUrl;
        private String youtubeChannelUrl;
}