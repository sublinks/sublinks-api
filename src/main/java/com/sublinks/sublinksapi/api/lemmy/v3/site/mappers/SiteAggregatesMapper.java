package com.sublinks.sublinksapi.api.lemmy.v3.site.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.site.models.SiteAggregates;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SiteAggregatesMapper extends Converter<LocalInstanceContext, SiteAggregates> {
    @Override
    @Mapping(target = "users_active_week", source = "context.instanceAggregate.activeWeeklyUserCount")
    @Mapping(target = "users_active_month", source = "context.instanceAggregate.activeMonthlyUserCount")
    @Mapping(target = "users_active_half_year", source = "context.instanceAggregate.activeHalfYearUserCount")
    @Mapping(target = "users_active_day", source = "context.instanceAggregate.activeDailyUserCount")
    @Mapping(target = "users", source = "context.instanceAggregate.userCount")
    @Mapping(target = "site_id", source = "context.instance.id")
    @Mapping(target = "posts", source = "context.instanceAggregate.postCount")
    @Mapping(target = "id", source = "context.instance.id")
    @Mapping(target = "communities", source = "context.instanceAggregate.communityCount")
    @Mapping(target = "comments", source = "context.instanceAggregate.commentCount")
    SiteAggregates convert(@Nullable LocalInstanceContext context);
}
