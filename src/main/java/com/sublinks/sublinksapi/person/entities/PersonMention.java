package com.sublinks.sublinksapi.person.entities;

import com.sublinks.sublinksapi.comment.entities.Comment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "person_mentions")
public class PersonMention {

  /**
   * Relationships.
   */
  @ManyToOne
  @JoinColumn(name = "recipient_id")
  private Person recipient;

  @ManyToOne
  @JoinColumn(name = "comment_id")
  private Comment comment;

  /**
   * Attributes.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "is_read")
  private boolean isRead;

  @CreationTimestamp
  @Column(updatable = false, name = "created_at")
  private Date createdAt;
}
