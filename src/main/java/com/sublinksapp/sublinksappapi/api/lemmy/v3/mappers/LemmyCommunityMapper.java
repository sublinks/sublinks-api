package com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.enums.SubscribedType;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Community;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.CommunityView;
import com.sublinksapp.sublinksappapi.community.CommunityAggregates;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LemmyCommunityMapper {

    @Mapping(target = "posting_restricted_to_mods", source = "community.postingRestrictedToMods")
    @Mapping(target = "name", source = "community.titleSlug")
    @Mapping(target = "instance_id", source = "community.instance.id")
    @Mapping(target = "hidden", constant = "false")
    @Mapping(target = "updated", source = "community.updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "published", source = "community.createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    //@Mapping(target = "banner", source = "community.bannerImageUrl")
    //@Mapping(target = "icon", source = "community.iconImageUrl")
    @Mapping(target = "inbox_url", constant = "https://aol.com")
    @Mapping(target = "followers_url", constant = "https://yahoo.com")
    @Mapping(target = "actor_id", constant = "https://google.com")
    @Mapping(target = "banner", ignore = true)
    @Mapping(target = "icon", ignore = true)
    Community communityToLemmyCommunity(com.sublinksapp.sublinksappapi.community.Community community);


    @Mapping(target = "subscribed", source = "subscribedType")
    @Mapping(target = "counts", source = "counts")
    @Mapping(target = "community", source = "community")
    @Mapping(target = "blocked", source = "blocked")
    CommunityView communityToCommunityView(
            com.sublinksapp.sublinksappapi.community.Community community,
            SubscribedType subscribedType,
            boolean blocked,
            CommunityAggregates counts);

    @Mapping(target = "users_active_week", source = "communityAggregates.activeWeeklyUserCount")
    @Mapping(target = "users_active_month", source = "communityAggregates.activeMonthlyUserCount")
    @Mapping(target = "users_active_half_year", source = "communityAggregates.activeHalfYearUserCount")
    @Mapping(target = "users_active_day", source = "communityAggregates.activeDailyUserCount")
    @Mapping(target = "subscribers", source = "communityAggregates.subscriberCount")
    @Mapping(target = "posts", source = "communityAggregates.postCount")
    @Mapping(target = "community_id", source = "communityAggregates.community.id")
    @Mapping(target = "comments", source = "communityAggregates.commentCount")
    @Mapping(target = "published", constant = "")
    @Mapping(target = "hot_rank", constant = "0l")
    com.sublinksapp.sublinksappapi.api.lemmy.v3.models.aggregates.CommunityAggregates map(CommunityAggregates communityAggregates);
}
