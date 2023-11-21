package com.sublinks.sublinksapi.person.dto;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentLike;
import com.sublinks.sublinksapi.instance.dto.Instance;
import com.sublinks.sublinksapi.language.dto.Language;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.enums.SortType;
import com.sublinks.sublinksapi.post.dto.PostLike;
import com.sublinks.sublinksapi.post.dto.PostRead;
import com.sublinks.sublinksapi.post.dto.PostSave;
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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.security.Principal;
import java.util.Collection;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "people")
public class Person implements UserDetails, Principal {

  /**
   * Relationships
   */
  @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
  Set<LinkPersonCommunity> linkPersonCommunity;

  @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
  Set<LinkPersonPost> linkPersonPost;

  @ManyToOne
  @JoinTable(
      name = "link_person_instances",
      joinColumns = @JoinColumn(name = "person_id"),
      inverseJoinColumns = @JoinColumn(name = "instance_id")
  )
  private Instance instance;

  @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
  private LinkPersonInstance linkPersonInstance;

  @OneToMany
  @PrimaryKeyJoinColumn
  private List<Comment> comments;

  @OneToMany(mappedBy = "person")
  @PrimaryKeyJoinColumn
  private List<CommentLike> commentLikes;

  @OneToMany(mappedBy = "person")
  @PrimaryKeyJoinColumn
  private List<CommentLike> commentReads;

  @OneToMany(mappedBy = "person")
  @PrimaryKeyJoinColumn
  private List<PostSave> postSaves;

  @OneToMany(mappedBy = "person")
  @PrimaryKeyJoinColumn
  private List<PostLike> postLikes;

  @OneToMany(mappedBy = "person")
  @PrimaryKeyJoinColumn
  private List<PostRead> postReads;

  @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private PersonAggregate personAggregate;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @JoinTable(
      name = "person_languages",
      joinColumns = @JoinColumn(name = "person_id"),
      inverseJoinColumns = @JoinColumn(name = "language_id")
  )
  private List<Language> languages;

  /**
   * Attributes
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, name = "is_local")
  private boolean isLocal;

  @Column(nullable = false, name = "is_bot_account")
  private boolean isBotAccount;

  @Column(nullable = false, name = "is_banned")
  private boolean isBanned;

  @Column(nullable = false, name = "is_deleted")
  private boolean isDeleted;

  @Column(nullable = false, name = "activity_pub_id")
  private String activityPubId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, name = "display_name")
  private String displayName;

  @Column(nullable = true)
  private String email;

  @Column(nullable = false, name = "is_email_verified")
  private boolean isEmailVerified;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String avatarImageUrl;

  @Column(nullable = false, name = "banner_image_url")
  private String bannerImageUrl;

  @Column(nullable = false)
  private String biography;

  @Column(nullable = false, name = "interface_language")
  private String interfaceLanguage;

  @Column(nullable = false, name = "default_theme")
  private String defaultTheme;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, name = "default_listing_type")
  private ListingType defaultListingType;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, name = "default_sort_type")
  private SortType defaultSortType;

  @Column(nullable = false, name = "is_show_scores")
  private boolean isShowScores;

  @Column(nullable = false, name = "is_show_read_posts")
  private boolean isShowReadPosts;

  @Column(nullable = false, name = "is_show_nsfw")
  private boolean isShowNsfw;

  @Column(nullable = false, name = "is_show_new_post_notifications")
  private boolean isShowNewPostNotifications;

  @Column(nullable = false, name = "is_show_bot_accounts")
  private boolean isShowBotAccounts;

  @Column(nullable = false, name = "is_show_avatars")
  private boolean isShowAvatars;

  @Column(nullable = false, name = "is_send_notifications_to_email")
  private boolean isSendNotificationsToEmail;

  @Column(nullable = false, name = "is_open_links_in_new_tab")
  private boolean isOpenLinksInNewTab;

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
  public Collection<? extends GrantedAuthority> getAuthorities() {

    return null; // @todo
  }

  @Override
  public String getPassword() {

    return this.password;
  }

  @Override
  public String getUsername() {

    return getName();
  }

  @Override
  public boolean isAccountNonExpired() {

    return true;
  }

  @Override
  public boolean isAccountNonLocked() {

    return isBanned();
  }

  @Override
  public boolean isCredentialsNonExpired() {

    return true;
  }

  @Override
  public boolean isEnabled() {

    return !isBanned();
  }

  @Override
  public final boolean equals(Object o) {

    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> oEffectiveClass =
        o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer()
            .getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
            .getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }
    Person person = (Person) o;
    return getId() != null && Objects.equals(getId(), person.getId());
  }

  @Override
  public final int hashCode() {

    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass().hashCode() : getClass().hashCode();
  }
}
