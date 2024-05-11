package com.sublinks.sublinksapi.api.sublinks.v1.community.models;

public record CommunityAggregatesResponse(String key,
                                          String subscriberCount,
                                          String postCount,
                                          String commentCount,
                                          String activeDailyUserCount,
                                          String activeWeeklyUserCount,
                                          String activeMonthlyUserCount,
                                          String activeHalfYearUserCount) {

}
