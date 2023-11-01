package com.sublinks.sublinksapi.post.dto;

import com.sublinks.sublinksapi.community.dto.Community;
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
@Table(name = "post_aggregates")
public class PostAggregate {
    /**
     * Relationships
     */
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name = "community_id")
    private Community community;

    /**
     * Attributes
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "comment_count")
    private int commentCount;

    @Column(nullable = false, name = "down_vote_count")
    private int downVoteCount;

    @Column(nullable = false, name = "up_vote_count")
    private int upVoteCount;

    @Column(nullable = false, name = "score")
    private int score;

    @Column(nullable = false, name = "hot_rank")
    private int hotRank;

    @Column(nullable = false, name = "hot_rank_active")
    private int hotRankActive;

    @Column(nullable = false, name = "controversy_rank")
    private int controversyRank;
}
