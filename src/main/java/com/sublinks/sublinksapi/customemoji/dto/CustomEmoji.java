package com.sublinks.sublinksapi.customemoji.dto;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "custom_emojis")
public class CustomEmoji {

  /*
   * Relationships.
   */
  @OneToMany(mappedBy = "emoji")
  List<CustomEmojiKeyword> keywords;

  /**
   * Attributes.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, name = "local_site_id")
  private Long localSiteId;

  @Column(nullable = false, name = "alt_text")
  private String altText;

  @Column(nullable = false, name = "image_url")
  private String imageUrl;

  @Column(nullable = false, name = "category")
  private String category;

  @Column(nullable = false, name = "shortcode", length = 128)
  private String shortCode;

  @CreationTimestamp
  @Column(updatable = false, nullable = false, name = "created_at")
  private Date createdAt;

  @UpdateTimestamp
  @Column(updatable = false, nullable = false, name = "updated_at")
  private Date updatedAt;

}
