package com.sublinks.sublinksapi.media.entities;

import com.sublinks.sublinksapi.person.entities.Person;
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
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Media class represents a media file associated with a Person.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "media")
public class Media {

  /**
   * Relationships
   */
  @ManyToOne
  @JoinColumn(name = "person_id")
  private Person person;

  /**
   * Attributes
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(nullable = false, name = "delete_token")
  private String deleteToken;

  @Column(nullable = false)
  private String file;

  @CreationTimestamp(source = SourceType.DB)
  @Column(updatable = false, nullable = false, name = "created_at")
  private Date createdAt;

  
  @UpdateTimestamp(source = SourceType.DB)
  @Column(updatable = false, name = "updated_at")
  private Date updatedAt;
}