package com.example.yachting.domain.yacht;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * Yacht command for adding/editing a yacht.
 * @author dp
 */
public class YachtCommand {

    @NotBlank(message = "Name is empty")
    private String modelName;
    private Long shipyardId;
    private Integer lengthOverallMeters;
    private Integer beamMeters;
    private String imageYoutubeId;

    /**
     * YachtCommand constructor.
     * @param modelName
     * @param shipyardId
     * @param lengthOverallMeters
     * @param beamMeters
     * @param imageYoutubeId
     */
    public YachtCommand(@JsonProperty("modelName") String modelName,
                        @JsonProperty("shipyardId") Long shipyardId,
                        @JsonProperty("lengthOverallMeters") Integer lengthOverallMeters,
                        @JsonProperty("beamMeters") Integer beamMeters,
                        @JsonProperty("imageYoutubeId") String imageYoutubeId
                        ) {
        this.modelName = modelName;
        this.lengthOverallMeters = lengthOverallMeters;
        this.beamMeters = beamMeters;
        this.shipyardId = shipyardId;
        this.imageYoutubeId = imageYoutubeId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Long getShipyardId() {
        return shipyardId;
    }

    public void setShipyardId(Long shipyardId) {
        this.shipyardId = shipyardId;
    }

    public Integer getLengthOverallMeters() {
        return lengthOverallMeters;
    }

    public void setLengthOverallMeters(Integer lengthOverallMeters) {
        this.lengthOverallMeters = lengthOverallMeters;
    }

    public Integer getBeamMeters() {
        return beamMeters;
    }

    public void setBeamMeters(Integer beamMeters) {
        this.beamMeters = beamMeters;
    }

    public String getImageYoutubeId() {
        return imageYoutubeId;
    }

    public void setImageYoutubeId(String imageYoutubeId) {
        this.imageYoutubeId = imageYoutubeId;
    }
}
