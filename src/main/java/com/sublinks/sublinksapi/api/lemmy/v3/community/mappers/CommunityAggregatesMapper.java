package com.sublinks.sublinksapi.api.lemmy.v3.community.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityAggregates;
import com.sublinks.sublinksapi.community.entities.CommunityAggregate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommunityAggregatesMapper extends
    Converter<CommunityAggregate, CommunityAggregates> {

  @Override
  @Mapping(target = "users_active_week", source = "communityAggregate.activeWeeklyUserCount")
  @Mapping(target = "users_active_month", source = "communityAggregate.activeMonthlyUserCount")
  @Mapping(target = "users_active_half_year", source = "communityAggregate.activeHalfYearUserCount")
  @Mapping(target = "users_active_day", source = "communityAggregate.activeDailyUserCount")
  @Mapping(target = "subscribers", source = "communityAggregate.subscriberCount")
  @Mapping(target = "posts", source = "communityAggregate.postCount")
  @Mapping(target = "community_id", source = "communityAggregate.community.id")
  @Mapping(target = "comments", source = "communityAggregate.commentCount")
  @Mapping(target = "published", constant = "")
  @Mapping(target = "hot_rank", constant = "0l")
  CommunityAggregates convert(@Nullable CommunityAggregate communityAggregate);
}
