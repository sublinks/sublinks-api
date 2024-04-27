package com.sublinks.sublinksapi.post.entities;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.instance.entities.Instance;
import com.sublinks.sublinksapi.language.entities.Language;
import com.sublinks.sublinksapi.person.entities.LinkPersonPost;
import com.sublinks.sublinksapi.shared.RemovedState;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post {

  /**
   * Relationships.
   */
  @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
  Set<LinkPersonPost> linkPersonPost;
  @ManyToOne
  @JoinTable(name = "post_post_cross_post", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "cross_post_id"))
  CrossPost crossPost;
  @ManyToOne
  @JoinColumn(name = "community_id")
  private Community community;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
  private List<Comment> comments;
  @ManyToOne
  private Instance instance;
  @ManyToOne
  @JoinColumn(name = "language_id")
  private Language language;
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "post")
  private PostAggregate postAggregate;
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  private List<PostLike> postLikes;
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  private List<PostHistory> postHistory;
  /**
   * Attributes.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, name = "activity_pub_id")
  private String activityPubId;

  @Column(nullable = false, name = "is_deleted")
  private boolean isDeleted;

  @Column(nullable = false, name = "removed_state")
  @Enumerated(EnumType.STRING)
  private RemovedState removedState;

  @Column(nullable = false, name = "is_local")
  private boolean isLocal;

  @Column(nullable = false, name = "is_locked")
  private boolean isLocked;

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

  @Override
  public final boolean equals(Object o) {

    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> objectEffectiveClass =
        o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer()
            .getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
            .getPersistentClass() : this.getClass();
    if (thisEffectiveClass != objectEffectiveClass) {
      return false;
    }
    Post post = (Post) o;
    return getId() != null && Objects.equals(getId(), post.getId());
  }

  @Override
  public final int hashCode() {

    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass().hashCode() : getClass().hashCode();
  }

  public boolean isRemoved() {

    return this.removedState != RemovedState.NOT_REMOVED;
  }
}
