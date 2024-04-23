package com.sublinks.sublinksapi.moderation.entities;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.ModlogActionType;
import com.sublinks.sublinksapi.instance.entities.Instance;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "moderation_logs")
public class ModerationLog {

  /**
   * Relationships.
   */
  @ManyToOne
  @JoinColumn(nullable = false, name = "instance_id")
  private Instance instance;

  /**
   * Attributes.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // This is a "string" as this could change easily
  @Column(nullable = false, name = "action_type")
  @Enumerated(EnumType.STRING)
  private ModlogActionType actionType;

  @Column(name = "reason")
  private String reason;

  @Column(name = "entity_id")
  private long entityId;

  @Column(name = "admin_person_id")
  private Long adminPersonId;

  @Column(name = "post_id")
  private Long postId;

  @Column(name = "comment_id")
  private Long commentId;

  @Column(name = "community_id")
  private Long communityId;

  @Column(name = "moderation_person_id")
  private Long moderationPersonId;

  @Column(name = "other_person_id")
  private Long otherPersonId;

  @Column(name = "removed")
  private boolean removed;

  @Column(name = "hidden")
  private boolean hidden;

  @Column(name = "locked")
  private boolean locked;

  @Column(name = "banned")
  private boolean banned;

  @Column(name = "featured")
  private boolean featured;

  @Column(name = "featured_community")
  private boolean featuredCommunity;

  @Column(name = "expires")
  private Date expires;

  @CreationTimestamp
  @Column(updatable = false, nullable = false, name = "created_at")
  private Date createdAt;

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
    ModerationLog that = (ModerationLog) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {

    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass()
        .hashCode() : getClass().hashCode();
  }
}
