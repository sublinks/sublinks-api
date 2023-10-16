package com.fedilinks.fedilinksapi.comment;

import com.fedilinks.fedilinksapi.comment.like.CommentLike;
import com.fedilinks.fedilinksapi.community.Community;
import com.fedilinks.fedilinksapi.person.Person;
import com.fedilinks.fedilinksapi.post.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comment implements Serializable {
    /**
     * Relationships
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "community_id")
    private Community community;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CommentLike> likes;

    /**
     * Attributes
     */
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

    @Column(nullable = false, name = "person_id", insertable = false, updatable = false)
    private Long personId;

    @Column(nullable = false, name = "community_id", insertable = false, updatable = false)
    private Long communityId;

    @Column(nullable = false, name = "post_id", insertable = false, updatable = false)
    private Long postId;

    @Column(nullable = false, name = "is_featured")
    private boolean isFeatured;

    @Column(nullable = false, name = "comment_body")
    private String commentBody;

    @Column(nullable = false)
    private String path;

    @CreationTimestamp
    @Column(updatable = false, nullable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(updatable = false, nullable = false, name = "updated_at")
    private Date updatedAt;
}
