package com.fedilinks.fedilinksapi.instance;

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
@Table(name = "instance_aggregates")
public class InstanceAggregate {
    /**
     * Relationships
     */
    @OneToOne
    @JoinColumn(name = "instance_id")
    private Instance instance;

    /**
     * Attributes
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "user_count")
    private int userCount;

    @Column(nullable = false, name = "post_count")
    private int postCount;

    @Column(nullable = false, name = "comment_count")
    private int commentCount;

    @Column(nullable = false, name = "community_count")
    private int communityCount;

    @Column(nullable = false, name = "active_daily_user_count")
    private int activeDailyUserCount;

    @Column(nullable = false, name = "active_weekly_user_count")
    private int activeWeeklyUserCount;

    @Column(nullable = false, name = "active_monthly_user_count")
    private int activeMonthlyUserCount;

    @Column(nullable = false, name = "active_half_year_user_count")
    private int activeHalfYearUserCount;
}
