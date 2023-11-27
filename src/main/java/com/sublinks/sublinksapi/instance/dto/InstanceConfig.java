package com.sublinks.sublinksapi.instance.dto;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.RegistrationMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "instance_configs")
public class InstanceConfig {

  /**
   * Relationships.
   */
  @OneToOne
  @JoinColumn(name = "instance_id")
  private Instance instance;

  /**
   * Attributes.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, name = "registration_mode")
  @Enumerated(EnumType.STRING)
  private RegistrationMode registrationMode;

  @Column(name = "registration_question")
  private String registrationQuestion;

  @CreationTimestamp
  @Column(nullable = false, name = "created_at")
  private Date createdAt;

  @UpdateTimestamp
  @Column(nullable = false, name = "updated_at")
  private Date updatedAt;
}
