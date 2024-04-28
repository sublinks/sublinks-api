package com.sublinks.sublinksapi.community.entities;

import com.sublinks.sublinksapi.comment.entities.Comment;
import com.sublinks.sublinksapi.instance.entities.Instance;
import com.sublinks.sublinksapi.language.entities.Language;
import com.sublinks.sublinksapi.person.entities.LinkPersonCommunity;
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
import java.io.Serializable;
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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "communities")
public class Community implements Serializable {

  @OneToMany(mappedBy = "community", fetch = FetchType.LAZY)
  @Fetch(FetchMode.SUBSELECT)
  Set<LinkPersonCommunity> linkPersonCommunity;
  /**
   * Relationships.
   */
  @ManyToOne
  @JoinColumn(name = "instance_id")
  private Instance instance;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "community")
  @Fetch(FetchMode.SUBSELECT)
  @PrimaryKeyJoinColumn
  private List<Comment> comments;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @PrimaryKeyJoinColumn
  private CommunityAggregate communityAggregate;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @Fetch(FetchMode.SUBSELECT)
  @JoinTable(name = "community_languages", joinColumns = @JoinColumn(name = "community_id"), inverseJoinColumns = @JoinColumn(name = "language_id"))
  private List<Language> languages;

  /**
   * Attributes.
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
    Community community = (Community) o;
    return getId() != null && Objects.equals(getId(), community.getId());
  }

  @Override
  public final int hashCode() {

    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass()
        .hashCode() : getClass().hashCode();
  }
}
