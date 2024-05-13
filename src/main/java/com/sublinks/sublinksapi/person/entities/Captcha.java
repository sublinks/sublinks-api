package com.sublinks.sublinksapi.person.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "captcha")
public class Captcha {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String uuid;

  @Column(nullable = false)
  private String word;

  @Column(nullable = false)
  private String png;

  @Column(nullable = true)
  private String wav;

  @Column(nullable = false)
  private boolean locked;

  @UpdateTimestamp(source = SourceType.DB)
  @Column(updatable = false, nullable = false, name = "updated_at")
  private Date updatedAt;
}
