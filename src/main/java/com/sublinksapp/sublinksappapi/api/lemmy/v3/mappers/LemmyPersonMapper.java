package com.sublinksapp.sublinksappapi.api.lemmy.v3.mappers;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.LocalUser;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.MyUserInfo;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.aggregates.PersonAggregates;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.CommunityBlockView;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.CommunityFollowerView;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.CommunityModeratorView;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.LocalUserView;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.PersonBlockView;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.PersonView;
import com.sublinksapp.sublinksappapi.community.Community;
import com.sublinksapp.sublinksappapi.person.Person;
import com.sublinksapp.sublinksappapi.person.PersonContext;
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


    @Mapping(target = "post_score", source = "personAggregates.postScore")
    @Mapping(target = "post_count", source = "personAggregates.postCount")
    @Mapping(target = "person_id", source = "personAggregates.person.id")
    @Mapping(target = "comment_score", source = "personAggregates.commentScore")
    @Mapping(target = "comment_count", source = "personAggregates.commentCount")
    PersonAggregates personAggregatesToPersonAggregates(com.sublinksapp.sublinksappapi.person.PersonAggregates personAggregates);

    @Mapping(target = "person", source = "personContext.person")
    @Mapping(target = "local_user", source = "personContext.person")
    @Mapping(target = "counts", source = "personContext.personAggregates")
    LocalUserView personToLocalUserView(PersonContext personContext);

    @Mapping(target = "local_user_view", source = "personContext")
    @Mapping(target = "discussion_languages", source = "personContext.discussLanguages")
    @Mapping(target = "moderates", source = "personContext.moderates")
    @Mapping(target = "follows", source = "personContext.follows")
    @Mapping(target = "community_blocks", source = "personContext.communityBlocks")
    @Mapping(target = "person_blocks", source = "personContext.personBlocks")
    MyUserInfo personToMyUserInfo(PersonContext personContext);

    @Mapping(target = "person", source = "personContext.person")
    @Mapping(target = "counts", source = "personContext.personAggregates")
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
    com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Person PersonToPerson(Person person);
}
