package com.sublinks.sublinksapi.moderation.dto;

import com.sublinks.sublinksapi.instance.dto.Instance;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    ModerationLog that = (ModerationLog) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {

    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass().hashCode() : getClass().hashCode();
  }
}
