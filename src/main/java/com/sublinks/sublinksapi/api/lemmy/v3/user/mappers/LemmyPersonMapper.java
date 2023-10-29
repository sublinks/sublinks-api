package com.sublinks.sublinksapi.api.lemmy.v3.user.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.community.mappers.LemmyCommunityMapper;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityBlockView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityFollowerView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.LocalUser;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.LocalUserView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.MyUserInfo;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonBlockView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonView;
import com.sublinks.sublinksapi.community.Community;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.person.PersonAggregate;
import com.sublinks.sublinksapi.person.PersonContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {SortTypeMapper.class, ListTypeMapper.class, LemmyCommunityMapper.class})
public interface LemmyPersonMapper {

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

    @Mapping(target = "totp_2fa_enabled", constant = "true")
    @Mapping(target = "post_listing_mode", constant = "List")
    @Mapping(target = "infinite_scroll_enabled", constant = "true")
    @Mapping(target = "enable_keyboard_navigation", constant = "true")
    @Mapping(target = "enable_animated_images",constant = "true")
    @Mapping(target = "blur_nsfw", constant = "true")
    @Mapping(target = "auto_expand", constant = "true")
    @Mapping(target = "admin", constant = "true")
    @Mapping(target = "person_id", source = "person.id")
    @Mapping(target = "email", source = "person.email")
    @Mapping(target = "theme", source = "person.defaultTheme")
    @Mapping(target = "validator_time", constant = "2023-06-09T02:35:26.397746Z", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
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


    @Mapping(target = "post_score", source = "personAggregate.postScore")
    @Mapping(target = "post_count", source = "personAggregate.postCount")
    @Mapping(target = "person_id", source = "personAggregate.person.id")
    @Mapping(target = "comment_score", source = "personAggregate.commentScore")
    @Mapping(target = "comment_count", source = "personAggregate.commentCount")
    PersonAggregates personAggregatesToPersonAggregates(PersonAggregate personAggregate);

    @Mapping(target = "person", source = "personContext.person")
    @Mapping(target = "local_user", source = "personContext.person")
    @Mapping(target = "counts", source = "personContext.personAggregate")
    LocalUserView personToLocalUserView(PersonContext personContext);

    @Mapping(target = "local_user_view", source = "personContext")
    @Mapping(target = "discussion_languages", source = "personContext.discussLanguages")
    @Mapping(target = "moderates", source = "personContext.moderates")
    @Mapping(target = "follows", source = "personContext.follows")
    @Mapping(target = "community_blocks", source = "personContext.communityBlocks")
    @Mapping(target = "person_blocks", source = "personContext.personBlocks")
    MyUserInfo personToMyUserInfo(PersonContext personContext);

    @Mapping(target = "person", source = "personContext.person")
    @Mapping(target = "counts", source = "personContext.personAggregate")
    PersonView personToPersonView(PersonContext personContext);

    @Mapping(target = "id", source = "person.id")
    @Mapping(target = "name", source = "person.name")
    @Mapping(target = "display_name", source = "person.displayName", conditionExpression = "java(!person.getDisplayName().isEmpty())")
    @Mapping(target = "avatar", source = "person.avatarImageUrl")
    @Mapping(target = "banned", source = "person.banned")
    @Mapping(target = "published", source = "person.createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "updated", source = "person.updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
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
    @Mapping(target = "instance_id", source = "person.instance.id")
    com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person personToPerson(Person person);
}
