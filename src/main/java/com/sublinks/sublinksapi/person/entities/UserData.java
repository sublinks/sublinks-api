package com.sublinks.sublinksapi.person.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_data")
public class UserData {

  /**
   * Relationships.
   */
  @ManyToOne
  @JoinColumn(name = "person_id")
  private Person person;

  /**
   * Attributes.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = true, name = "ip_address")
  private String ipAddress;

  @Column(nullable = true, name = "user_agent")
  private String userAgent;

  @Column(nullable = false, name = "token")
  private String token;

  @Column(nullable = false, name = "active")
  private boolean active;

  @CreationTimestamp
  @Column(updatable = false, nullable = false, name = "created_at")
  private Date createdAt;

  @UpdateTimestamp
  @Column(updatable = true, nullable = false, name = "last_used_at")
  private Date lastUsedAt;
}
