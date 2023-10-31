package com.sublinks.sublinksapi.api.lemmy.v3.community.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SubscribedType;
import com.sublinks.sublinksapi.community.CommunityAggregate;
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
    @Mapping(target = "actor_id", source = "activityPubId")
    //@Mapping(target = "banner", source = "community.bannerImageUrl")
    //@Mapping(target = "icon", source = "community.iconImageUrl")
    @Mapping(target = "inbox_url", constant = "https://aol.com")
    @Mapping(target = "followers_url", constant = "https://yahoo.com")
    @Mapping(target = "banner", ignore = true)
    @Mapping(target = "icon", ignore = true)
    Community communityToLemmyCommunity(com.sublinks.sublinksapi.community.Community community);


    @Mapping(target = "subscribed", source = "subscribedType")
    @Mapping(target = "counts", source = "counts")
    @Mapping(target = "community", source = "community")
    @Mapping(target = "blocked", source = "blocked")
    CommunityView communityToCommunityView(
            com.sublinks.sublinksapi.community.Community community,
            SubscribedType subscribedType,
            boolean blocked,
            CommunityAggregate counts);

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
    com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityAggregates map(CommunityAggregate communityAggregate);
}
