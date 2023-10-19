package com.fedilinks.fedilinksapi.community;

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
@Table(name = "community_aggregates")
public class CommunityAggregates {
    /**
     * Relationships
     */
    @OneToOne
    @JoinColumn(name = "community_id")
    private Community community;

    /**
     * Attributes
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "subscriber_count")
    private int subscriberCount;

    @Column(nullable = false, name = "post_count")
    private int postCount;

    @Column(nullable = false, name = "comment_count")
    private int commentCount;

    @Column(nullable = false, name = "active_daily_user_count")
    private int activeDailyUserCount;

    @Column(nullable = false, name = "active_weekly_user_count")
    private int activeWeeklyUserCount;

    @Column(nullable = false, name = "active_monthly_user_count")
    private int activeMonthlyUserCount;

    @Column(nullable = false, name = "active_half_year_user_count")
    private int activeHalfYearUserCount;
}
