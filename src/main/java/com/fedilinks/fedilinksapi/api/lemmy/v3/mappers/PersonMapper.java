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
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Collection;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {

    @Mapping(target = "person", source = "person")
    @Mapping(target = "community", source = "community")
    CommunityBlockView personToCommunityBlockView(@Context Person person, Collection<Community> community);

    @Mapping(target = "moderator", source = "person")
    @Mapping(target = "community", source = "community")
    CommunityModeratorView personToCommunityModeratorView(@Context Person person, Collection<Community> community);

    @Mapping(target = "follower", source = "person")
    @Mapping(target = "community", source = "community")
    CommunityFollowerView personToCommunityFollowerView(@Context Person person, Collection<Community> community);

    @Mapping(target = "target", source = "target")
    @Mapping(target = "person", source = "person")
    PersonBlockView personToPersonBlockView(@Context Person person, Collection<Person> target);

    @Mapping(target = "person_id", source = "person.id")
    @Mapping(target = "email", source = "")
    @Mapping(target = "theme", source = "")
    @Mapping(target = "validator_time", constant = "")
    @Mapping(target = "totp_2fa_url", constant = "")
    @Mapping(target = "interface_language", source = "")
    @Mapping(target = "default_sort_type", source = "")
    @Mapping(target = "default_listing_type", source = "")
    @Mapping(target = "show_scores", source = "")
    @Mapping(target = "show_read_posts", source = "")
    @Mapping(target = "show_nsfw", source = "")
    @Mapping(target = "show_new_post_notifs", source = "")
    @Mapping(target = "show_bot_accounts", source = "")
    @Mapping(target = "show_avatars", source = "")
    @Mapping(target = "send_notifications_to_email", source = "")
    @Mapping(target = "open_links_in_new_tab", source = "")
    @Mapping(target = "email_verified", source = "")
    @Mapping(target = "accepted_application", source = "")
    LocalUser personToLocalUser(Person person);

    @Mapping(target = "person", source = "")
    @Mapping(target = "local_user", source = "")
    @Mapping(target = "counts", source = "")
    LocalUserView personToLocalUserView(Person person);

    @Mapping(target = "local_user_view", source = "")
    @Mapping(target = "discussion_languages", source = "")
    @Mapping(target = "moderates", source = "")
    @Mapping(target = "follows", source = "")
    @Mapping(target = "community_blocks", source = "")
    @Mapping(target = "person_blocks", source = "")
    MyUserInfo personToMyUserInfo(Person person);

    @Mapping(target = "person", source = "person")
    @Mapping(target = "counts", source = "personAggregates")
    PersonView personToPersonView(Person person, PersonAggregates personAggregates);

    @Mapping(target = "updated", source = "updatedAt")
    @Mapping(target = "shared_inbox_url", constant = "")
    @Mapping(target = "published", source = "createdAt")
    @Mapping(target = "matrix_user_id", constant = "")
    @Mapping(target = "instance_id", expression = "java((long)1)")
    @Mapping(target = "inbox_url", constant = "")
    @Mapping(target = "display_name", source = "name")
    @Mapping(target = "deleted", constant = "false")
    @Mapping(target = "bot_account", constant = "false")
    @Mapping(target = "bio", constant = "")
    @Mapping(target = "banner", constant = "")
    @Mapping(target = "ban_expires", constant = "")
    @Mapping(target = "avatar", constant = "")
    @Mapping(target = "admin", constant = "true")
    @Mapping(target = "actor_id", constant = "")
    com.fedilinks.fedilinksapi.api.lemmy.v3.models.Person PersonToPerson(Person person);
}
