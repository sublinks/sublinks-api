package com.sublinks.sublinksapi.person;

import jakarta.persistence.Column;
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
@Table(name = "person_aggregates")
public class PersonAggregate {
    /**
     * Relationships
     */
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    /**
     * Attributes
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "post_count")
    private int postCount;

    @Column(nullable = false, name = "comment_count")
    private int commentCount;

    @Column(nullable = false, name = "post_score")
    private int postScore;

    @Column(nullable = false, name = "comment_score")
    private int commentScore;
}
