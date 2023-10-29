package com.sublinks.sublinksapi.post;

import com.sublinks.sublinksapi.comment.Comment;
import com.sublinks.sublinksapi.community.Community;
import com.sublinks.sublinksapi.instance.Instance;
import com.sublinks.sublinksapi.language.Language;
import com.sublinks.sublinksapi.person.LinkPersonPost;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post {
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    Set<LinkPersonPost> linkPersonPost;
    /**
     * Relationships
     */
    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;
    @OneToMany(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private List<Comment> comments;
    @ManyToOne
    private Instance instance;
    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PostAggregates postAggregates;
    /**
     * Attributes
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "activity_pub_id")
    private String activityPubId;

    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted;

    @Column(nullable = false, name = "is_removed")
    private boolean isRemoved;

    @Column(nullable = false, name = "is_local")
    private boolean isLocal;

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
