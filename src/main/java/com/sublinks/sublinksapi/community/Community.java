package com.sublinks.sublinksapi.community;

import com.sublinks.sublinksapi.comment.Comment;
import com.sublinks.sublinksapi.instance.Instance;
import com.sublinks.sublinksapi.language.Language;
import com.sublinks.sublinksapi.person.LinkPersonCommunity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
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
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "communities")
public class Community implements Serializable {
    /**
     * Relationships
     */
    @ManyToOne
    @JoinColumn(name = "instance_id")
    private Instance instance;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "community")
    @PrimaryKeyJoinColumn
    private List<Comment> comments;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private CommunityAggregates communityAggregates;

    @OneToMany(mappedBy = "community", fetch = FetchType.EAGER)
    Set<LinkPersonCommunity> linkPersonCommunity;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "community_languages",
            joinColumns = @JoinColumn(name = "community_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private List<Language> languages;

    /**
     * Attributes
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "activity_pub_id")
    private String activityPubId;

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

    @Column(nullable = false, name = "icon_image_url")
    private String iconImageUrl;

    @Column(nullable = false, name = "banner_image_url")
    private String bannerImageUrl;

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
