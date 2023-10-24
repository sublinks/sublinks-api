package com.sublinks.sublinksapi.comment;

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
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comment_aggregates")
public class CommentAggregate {
    /**
     * Relationships
     */
    @OneToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    /**
     * Attributes
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
}
