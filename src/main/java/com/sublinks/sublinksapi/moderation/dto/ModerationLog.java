package com.sublinks.sublinksapi.moderation.dto;

import com.sublinks.sublinksapi.instance.dto.Instance;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "moderation_logs")
public class ModerationLog {
    /**
     * Relationships
     */
    @ManyToOne
    private Instance instance;

    /**
     * Attributes
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "action_type")
    private String actionType;

    private String reason;

    @Column(name = "entity_id")
    private long entityId;

    @Column(name = "admin_person_id")
    private long adminPersonId;

    @Column(name = "post_id")
    private long postId;

    @Column(name = "community_id")
    private long communityId;

    @Column(name = "moderation_person_id")
    private long moderationPersonId;

    @Column(name = "other_person_id")
    private long otherPersonId;

    private boolean removed;

    private boolean hidden;

    private boolean locked;

    private boolean banned;

    private boolean featured;

    private Date expires;

    @CreationTimestamp
    @Column(updatable = false, nullable = false, name = "created_at")
    private Date createdAt;
}
