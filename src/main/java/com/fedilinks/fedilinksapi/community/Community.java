package com.fedilinks.fedilinksapi.community;

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

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "communities")
public class Community implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "activity_pub_id")
    private String activityPubId;

    @Column(nullable = false, name = "instance_id")
    private Long instanceId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, name = "title_slug")
    private String titleSlug;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted;

    @Column(nullable = false, name = "is_removed")
    private boolean isRemoved;

    @Column(nullable = false, name = "is_local")
    private boolean isLocal;

    @Column(nullable = false, name = "is_nsfw")
    private boolean isNsfw;

    @Column(nullable = false, name = "is_posting_restricted_to_mods")
    private boolean isPostingRestrictedToMods;

    @Column(nullable = false, name = "nsfw_type")
    private NsfwType nsfwType;

    @Column(nullable = false, name = "icon_image_url")
    private String iconImageUrl;

    @Column(nullable = false, name = "banner_image_url")
    private String bannerImageUrl;

    @CreationTimestamp
    @Column(updatable = false, nullable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(updatable = false, nullable = false, name = "updated_at")
    private Date updatedAt;
}
