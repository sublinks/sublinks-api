package com.sublinks.sublinksapi.instance.dto;

import com.sublinks.sublinksapi.language.dto.Language;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "instance_languages")
public class InstanceLanguage {
    /**
     * Relationships
     */
    @OneToOne
    @JoinColumn(name = "instance_id")
    private Instance instance;

    @OneToOne
    @JoinColumn(name = "language_id")
    private Language language;

    /**
     * Attributes
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
