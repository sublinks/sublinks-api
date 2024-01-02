package com.sublinks.sublinksapi.api.lemmy.v3.user.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
  @Column(nullable = false)
  private String uuid;
  @Column(nullable = false)
  private String word;
  @Column(nullable = false)
  private String png;
  @Column(nullable = true)
  private String wav;
  @Column(nullable = false)
  private boolean locked = false;
}
