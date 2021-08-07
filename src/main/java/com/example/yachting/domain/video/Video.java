package com.example.yachting.domain.video;

import com.example.yachting.domain.yacht.Yacht;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Video entity class.
 * @author dp
 */
@Entity
@Table(name="videos")
@Data
@NoArgsConstructor
public class Video {

    /**
     *  Unique id - primary key of a video.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Video's title.
     */
    @Column(name="title", nullable = false)
    private String title;

    /**
     * Id of a video on youtube.
     */
    @Column(name="youtube_id", unique = true, nullable = false)
    private String youtubeId;

    /**
     * Id of a youtube channel where video is uploaded.
     */
    @Column(name="youtube_channel_id")
    private String youtubeChannelId;

    /**
     * When was video published at youtube.
     */
    @Column(name="youtube_published_at")
    private LocalDateTime youtubePublishedAt;

    /**
     * Yacht entity linked to video.
     * Can be null if no yachts are linked to video.
     */
    @ManyToOne
    @JoinColumn(
            name = "yacht_id",
            referencedColumnName = "id"
    )
    private Yacht yacht;

    /**
     * When was video imported/fetched from youtube.
     */
    @Column(name="imported_at")
    private LocalDateTime importedAt;

    /**
     * Activation status of video.
     */
    @Column(name="active", columnDefinition = "boolean default true")
    private Boolean active = true;

    /**
     * When was activation status last updated.
     */
    @Column(name="active_updated_at")
    private LocalDateTime activeUpdatedAt;

    /**
     * Publishing status of video.
     */
    @Column(name="published", columnDefinition = "boolean default false")
    private Boolean published = false;

    /**
     * When was publishing status last updated.
     */
    @Column(name="published_updated_at")
    private LocalDateTime publishedUpdatedAt;

    /**
     * Constructor without id.
     * @param title
     * @param youtubeId
     * @param youtubeChannelId
     * @param youtubePublishedAt
     * @param yacht
     * @param importedAt
     * @param active
     * @param activeUpdatedAt
     * @param published
     * @param publishedUpdatedAt
     */
    public Video(String title, String youtubeId, String youtubeChannelId, LocalDateTime youtubePublishedAt, Yacht yacht, LocalDateTime importedAt, Boolean active, LocalDateTime activeUpdatedAt, Boolean published, LocalDateTime publishedUpdatedAt) {
        this.title = title;
        this.youtubeId = youtubeId;
        this.youtubeChannelId = youtubeChannelId;
        this.youtubePublishedAt = youtubePublishedAt;
        this.yacht = yacht;
        this.importedAt = importedAt;
        this.active = active;
        this.activeUpdatedAt = activeUpdatedAt;
        this.published = published;
        this.publishedUpdatedAt = publishedUpdatedAt;
    }

    /**
     * Constructor - builder pattern.
     * @param builder video builder
     */
    private Video(VideoBuilder builder) {
        this.title = builder.title;
        this.youtubeId = builder.youtubeId;
        this.youtubeChannelId = builder.youtubeChannelId;
        this.yacht = builder.yacht;
        this.importedAt = builder.importedAt;
        this.youtubePublishedAt = builder.youtubePublishedAt;
    }

    /**
     * Video builder.
     */
    public static class VideoBuilder {
        private String title;
        private String youtubeId;
        private String youtubeChannelId;
        private Yacht yacht;
        private LocalDateTime importedAt;
        private LocalDateTime youtubePublishedAt;

        public VideoBuilder() {}

        public VideoBuilder setTitle(String title) {
            this.title = title;
            return this;
        }
        public VideoBuilder setYoutubeId(String youtubeId) {
            this.youtubeId = youtubeId;
            return this;
        }
        public VideoBuilder setYoutubeChannelId(String youtubeChannelId) {
            this.youtubeChannelId = youtubeChannelId;
            return this;
        }
        public VideoBuilder setYacht(Yacht yacht) {
            this.yacht = yacht;
            return this;
        }
        public VideoBuilder setImportedAt(LocalDateTime importedAt) {
            this.importedAt = importedAt;
            return this;
        }
        public VideoBuilder setYoutubePublishedAt(LocalDateTime youtubePublishedAt) {
            this.youtubePublishedAt = youtubePublishedAt;
            return this;
        }

        public Video build() {
            return new Video(this);
        }
    }
}
