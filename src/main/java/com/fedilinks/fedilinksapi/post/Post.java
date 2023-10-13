package com.fedilinks.fedilinksapi.post;

import com.fedilinks.fedilinksapi.enums.NsfwType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "activity_pub_id")
    private String activityPubId;

    @Column(nullable = false, name = "language_id")
    private Long languageId;

    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted;

    @Column(nullable = false, name = "is_removed")
    private boolean isRemoved;

    @Column(nullable = false, name = "creator_id")
    private Long creatorId;

    @Column(nullable = false, name = "community_id")
    private Long communityId;

    @Column(nullable = false, name = "is_featured")
    private boolean isFeatured;

    @Column(nullable = false, name = "is_featured_in_community")
    private boolean isFeaturedInCommunity;

    @Column(nullable = false, name = "link_url")
    private String linkUrl;

    @Column(nullable = false, name = "link_title")
    private String linkTitle;

    @Column(nullable = false, name = "link_description")
    private String linkDescription;

    @Column(nullable = false, name = "link_thumbnail_url")
    private String linkThumbnailUrl;

    @Column(nullable = false, name = "link_video_url")
    private String linkVideoUrl;

    @Column(nullable = false, name = "is_nsfw")
    private boolean isNsfw;

    @Column(nullable = false, name = "nsfw_type")
    private NsfwType nsfwType;

    private String title;

    @Column(nullable = false, name = "title_slug")
    private String titleSlug;

    @Column(nullable = false, name = "post_body")
    private String postBody;

    @Column(nullable = false, name = "public_key")
    private String publicKey;

    @Column(nullable = true, name = "private_key")
    private String privateKey;

    @CreationTimestamp
    @Column(updatable = false, nullable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(updatable = false, nullable = false, name = "updated_at")
    private Date updatedAt;
}
