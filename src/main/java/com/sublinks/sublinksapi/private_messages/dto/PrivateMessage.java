package com.sublinks.sublinksapi.private_messages.dto;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentLike;
import com.sublinks.sublinksapi.instance.dto.Instance;
import com.sublinks.sublinksapi.language.dto.Language;
import com.sublinks.sublinksapi.person.dto.*;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.enums.SortType;
import com.sublinks.sublinksapi.post.dto.PostLike;
import com.sublinks.sublinksapi.post.dto.PostRead;
import com.sublinks.sublinksapi.post.dto.PostSave;
import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "private_message")
public class PrivateMessage {
    /**
     * Relationships
     */
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    Person recipient;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    Person sender;

    /**
     * Attributes
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "content")
    private String content;

    @Column(nullable = false, name = "is_local")
    private boolean local;

    @Column(nullable = false, name = "read")
    private boolean read;

    @Column(updatable = false, nullable = false, name = "created_at")
    private Date publishedAt;

    @Column(updatable = false, nullable = false, name = "updated_at")
    private Date updatedAt;

    @Column(nullable = false, name = "deleted")
    private boolean deleted;
}
