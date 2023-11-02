package com.sublinks.sublinksapi.api.lemmy.v3.site.services;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityBlockView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityFollowerView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.LocalUser;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.LocalUserView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.MyUserInfo;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonBlockView;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.person.enums.LinkPersonCommunityType;
import com.sublinks.sublinksapi.person.models.PersonContext;
import com.sublinks.sublinksapi.person.services.LinkPersonCommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MyUserInfoService {
    private final ConversionService conversionService;
    private final PersonContext personContext;
    private final LinkPersonCommunityService linkPersonCommunityService;

    public MyUserInfo getMyUserInfo() {
        return MyUserInfo.builder()
                .local_user_view(LocalUserView.builder()
                        .local_user(conversionService.convert(personContext.getPerson(), LocalUser.class))
                        .person(conversionService.convert(personContext.getPerson(), Person.class))
                        .counts(conversionService.convert(personContext.getPersonAggregate(), PersonAggregates.class))
                        .build())
                .follows(getUserCommunityFollows())
                .moderates(getUserCommunityModerates())
                .community_blocks(getUserCommunitiesBlocked())
                .person_blocks(getUserPeopleBlocked())
                .discussion_languages(personContext.getDiscussLanguages())
                .build();
    }

    public Collection<CommunityFollowerView> getUserCommunityFollows() {
        Collection<Community> communities = linkPersonCommunityService.getPersonLinkByType(personContext.getPerson(), LinkPersonCommunityType.follower);

        Collection<CommunityFollowerView> communityFollowerViews = new ArrayList<>();
        for (Community community : communities) {
            communityFollowerViews.add(CommunityFollowerView.builder()
                    .follower(conversionService.convert(personContext.getPerson(), Person.class))
                    .community(conversionService.convert(community, com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community.class))
                    .build());
        }

        return communityFollowerViews;
    }

    public Collection<CommunityModeratorView> getUserCommunityModerates() {
        Collection<Community> communities = linkPersonCommunityService.getPersonLinkByType(personContext.getPerson(), LinkPersonCommunityType.moderator);

        Collection<CommunityModeratorView> communityModeratorViews = new ArrayList<>();
        for (Community community : communities) {
            communityModeratorViews.add(
                    CommunityModeratorView.builder()
                            .moderator(conversionService.convert(personContext.getPerson(), Person.class))
                            .community(conversionService.convert(community, com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community.class))
                            .build());
        }
        return communityModeratorViews;
    }

    public Collection<CommunityBlockView> getUserCommunitiesBlocked() {
        Collection<Community> communities = linkPersonCommunityService.getPersonLinkByType(personContext.getPerson(), LinkPersonCommunityType.blocked);

        Collection<CommunityBlockView> communityBlockViews = new ArrayList<>();
        for (Community community : communities) {
            communityBlockViews.add(CommunityBlockView.builder()
                    .person(conversionService.convert(personContext.getPerson(), Person.class))
                    .community(conversionService.convert(community, com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community.class))
                    .build());
        }
        return communityBlockViews;
    }

    public Collection<PersonBlockView> getUserPeopleBlocked() {
        return new ArrayList<>(); // @todo people blocks
    }
}
