package com.sublinks.sublinksapi.api.sublinks.v1.community.models;

public record CommunityAggregatesResponse(
    String communityKey,
    Integer subscriberCount,
    Integer postCount,
    Integer commentCount,
    Integer activeDailyUserCount,
    Integer activeWeeklyUserCount,
    Integer activeMonthlyUserCount,
    Integer activeHalfYearUserCount) {

}
