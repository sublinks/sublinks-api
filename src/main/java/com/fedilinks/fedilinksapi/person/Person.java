package com.fedilinks.fedilinksapi.person;

import com.fedilinks.fedilinksapi.comment.Comment;
import com.fedilinks.fedilinksapi.comment.CommentLike;
import com.fedilinks.fedilinksapi.instance.Instance;
import com.fedilinks.fedilinksapi.language.Language;
import com.fedilinks.fedilinksapi.person.enums.ListingType;
import com.fedilinks.fedilinksapi.person.enums.SortType;
import com.fedilinks.fedilinksapi.post.Post;
import com.fedilinks.fedilinksapi.post.PostLike;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    @ManyToOne
    @JoinColumn(name = "instance_id")
    private Instance instance;

    @OneToMany
    @PrimaryKeyJoinColumn
    private List<Comment> comments;

    @OneToMany
    @PrimaryKeyJoinColumn
    private List<Post> posts;

    @OneToMany
    @PrimaryKeyJoinColumn
    private List<CommentLike> commentLikes;

    @OneToMany
    @PrimaryKeyJoinColumn
    private List<PostLike> postLikes;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PersonAggregates personAggregates;

    @OneToMany(mappedBy = "person")
    Set<LinkPersonCommunity> linkPersonCommunity;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

    @Column(nullable = false, name = "default_listing_type")
    private ListingType defaultListingType;

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
}
