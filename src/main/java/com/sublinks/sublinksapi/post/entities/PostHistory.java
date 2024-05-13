package com.sublinks.sublinksapi.post.entities;

import com.sublinks.sublinksapi.shared.RemovedState;
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
import org.hibernate.annotations.SourceType;
import org.hibernate.proxy.HibernateProxy;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "post_history")
public class PostHistory {

  /**
   * Relationships.
   */
  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;
  /**
   * Attributes.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, name = "title")
  private String title;

  @Column(nullable = false, name = "body")
  private String body;

  @Column(nullable = true, name = "url")
  private String url;

  @Column(nullable = false, name = "removed_state")
  @Enumerated(EnumType.STRING)

  private RemovedState removedState;

  @Column(nullable = false, name = "is_deleted")
  private Boolean isDeleted;

  @Column(nullable = false, name = "is_nsfw")
  private Boolean isNsfw;

  @Column(nullable = false, name = "is_locked")
  private Boolean isLocked;

  @CreationTimestamp(source = SourceType.DB)
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
    PostHistory post = (PostHistory) o;
    return getId() != null && Objects.equals(getId(), post.getId());
  }

  @Override
  public final int hashCode() {

    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass()
        .hashCode() : getClass().hashCode();
  }
}
