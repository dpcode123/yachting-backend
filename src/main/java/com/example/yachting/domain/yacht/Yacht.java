package com.example.yachting.domain.yacht;

import com.example.yachting.domain.shipyard.Shipyard;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Yacht entity class.
 * @author dp
 */
@Entity
@Table(name="yachts")
@Data
@NoArgsConstructor
public class Yacht implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique id - primary key of a yacht.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Yacht's shipyard.
     */
    @ManyToOne
    @JoinColumn(
            name = "shipyard_id",
            referencedColumnName = "id"
    )
    private Shipyard shipyard;

    /**
     * Yacht's model name.
     */
    @Column(name="model_name")
    private String modelName;

    /**
     * Overall length of yacht in meters.
     */
    @Column(name="length_overall_meters")
    private Integer lengthOverallMeters;

    /**
     * Beam width of yacht in meters.
     */
    @Column(name="beam_meters")
    private Integer beamMeters;

    /**
     * Yacht's picture. Since no images are hosted and served from backend,
     * each yacht has embedded youtube image (video thumbnail) as a picture.
     */
    @Column(name="image_youtube_id")
    private String imageYoutubeId;

    /**
     * Constructor without id.
     * @param shipyard
     * @param modelName
     * @param lengthOverallMeters
     * @param beamMeters
     * @param imageYoutubeId
     */
    public Yacht(Shipyard shipyard, String modelName, Integer lengthOverallMeters, Integer beamMeters, String imageYoutubeId) {
        this.shipyard = shipyard;
        this.modelName = modelName;
        this.lengthOverallMeters = lengthOverallMeters;
        this.beamMeters = beamMeters;
        this.imageYoutubeId = imageYoutubeId;
    }

}
