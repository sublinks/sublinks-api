package com.sublinks.sublinksapi.api.sublinks.v1.community.models;

public record CommunityAggregatesResponse(Long id,
                                          int subscribers,
                                          int posts,
                                          int comments,
                                          int activeDailyUsers,
                                          int activeWeeklyUsers,
                                          int activeMonthlyUsers,
                                          int activeHalfYearlyUsers) {

}
