package com.sublinks.sublinksapi.comment.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comment_aggregates")
public class CommentAggregate {

  /**
   * Relationships.
   */
  @OneToOne
  @JoinColumn(name = "comment_id")
  private Comment comment;

  /**
   * Attributes.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, name = "up_votes")
  private int upVotes;

  @Column(nullable = false, name = "down_votes")
  private int downVotes;

  @Column(nullable = false)
  private int score;

  @Column(nullable = false, name = "children_count")
  private int childrenCount;

  @Column(nullable = false, name = "hot_rank")
  private int hotRank;

  @Column(nullable = false, name = "controversy_rank")
  private int controversyRank;

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
    CommentAggregate that = (CommentAggregate) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {

    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass().hashCode() : getClass().hashCode();
  }
}
