package com.fedilinks.fedilinksapi.api.lemmy.v3.mappers;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.LocalUser;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.MyUserInfo;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.aggregates.PersonAggregates;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommunityBlockView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommunityFollowerView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommunityModeratorView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.LocalUserView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PersonBlockView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PersonView;
import com.fedilinks.fedilinksapi.community.Community;
import com.fedilinks.fedilinksapi.person.Person;
import com.fedilinks.fedilinksapi.person.SignedInUserContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {SortTypeMapper.class, ListTypeMapper.class, PersonMapper.class})
public interface PersonMapper {

    @Mapping(target = "person", source = "person")
    @Mapping(target = "community", source = "community")
    CommunityBlockView personToCommunityBlockView(Person person, Community community);

    @Mapping(target = "moderator", source = "person")
    @Mapping(target = "community", source = "community")
    CommunityModeratorView personToCommunityModeratorView(Person person, Community community);

    @Mapping(target = "follower", source = "person")
    @Mapping(target = "community", source = "community")
    CommunityFollowerView personToCommunityFollowerView(Person person, Community community);

    @Mapping(target = "target", source = "target")
    @Mapping(target = "person", source = "person")
    PersonBlockView personToPersonBlockView(Person person, Person target);

    @Mapping(target = "person_id", source = "person.id")
    @Mapping(target = "email", source = "person.email")
    @Mapping(target = "theme", source = "person.defaultTheme")
    @Mapping(target = "validator_time", constant = "")
    @Mapping(target = "totp_2fa_url", constant = "")
    @Mapping(target = "interface_language", source = "person.interfaceLanguage")
    @Mapping(target = "default_sort_type", source = "person.defaultSortType")
    @Mapping(target = "default_listing_type", source = "person.defaultListingType")
    @Mapping(target = "show_scores", source = "person.showScores")
    @Mapping(target = "show_read_posts", source = "person.showReadPosts")
    @Mapping(target = "show_nsfw", source = "person.showNsfw")
    @Mapping(target = "show_new_post_notifs", source = "person.showNewPostNotifications")
    @Mapping(target = "show_bot_accounts", source = "person.showBotAccounts")
    @Mapping(target = "show_avatars", source = "person.showAvatars")
    @Mapping(target = "send_notifications_to_email", source = "person.sendNotificationsToEmail")
    @Mapping(target = "open_links_in_new_tab", source = "person.openLinksInNewTab")
    @Mapping(target = "email_verified", source = "person.emailVerified")
    @Mapping(target = "accepted_application", constant = "true")
    LocalUser personToLocalUser(Person person);

    @Mapping(target = "person", source = "userContext.person")
    @Mapping(target = "local_user", source = "userContext.person")
    @Mapping(target = "counts", source = "userContext.personAggregates")
    LocalUserView personToLocalUserView(SignedInUserContext userContext);

    @Mapping(target = "local_user_view", source = "userContext")
    @Mapping(target = "discussion_languages", source = "userContext.discussLanguages")
    @Mapping(target = "moderates", source = "userContext.moderates")
    @Mapping(target = "follows", source = "userContext.follows")
    @Mapping(target = "community_blocks", source = "userContext.communityBlocks")
    @Mapping(target = "person_blocks", source = "userContext.personBlocks")
    MyUserInfo personToMyUserInfo(SignedInUserContext userContext);

    @Mapping(target = "person", source = "person")
    @Mapping(target = "counts", source = "personAggregates")
    PersonView personToPersonView(Person person, PersonAggregates personAggregates);

    @Mapping(target = "id", source = "person.id")
    @Mapping(target = "name", source = "person.name")
    @Mapping(target = "display_name", source = "person.displayName", conditionExpression = "java(!person.getDisplayName().isEmpty())")
    @Mapping(target = "avatar", source = "person.avatarImageUrl")
    @Mapping(target = "banned", source = "person.banned")
    @Mapping(target = "published", source = "person.createdAt")
    @Mapping(target = "updated", source = "person.updatedAt")
    @Mapping(target = "actor_id", constant = "")
    @Mapping(target = "bio", source = "person.biography")
    @Mapping(target = "local", source = "person.local")
    @Mapping(target = "banner", source = "person.bannerImageUrl")
    @Mapping(target = "deleted", source = "person.deleted")
    @Mapping(target = "inbox_url", constant = "")
    @Mapping(target = "shared_inbox_url", constant = "")
    @Mapping(target = "matrix_user_id", constant = "")
    @Mapping(target = "admin", constant = "true")
    @Mapping(target = "bot_account", source = "person.botAccount")
    @Mapping(target = "ban_expires", constant = "")
    @Mapping(target = "instance_id", source = "person.instanceId")
    com.fedilinks.fedilinksapi.api.lemmy.v3.models.Person PersonToPerson(Person person);
}
