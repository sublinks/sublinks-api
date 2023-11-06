package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityBlockView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityFollowerView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityModeratorView;
import lombok.Builder;

import java.util.Collection;

@Builder
public record MyUserInfo(
        LocalUserView local_user_view,
        Collection<CommunityFollowerView> follows,
        Collection<CommunityModeratorView> moderates,
        Collection<CommunityBlockView> community_blocks,
        Collection<PersonBlockView> person_blocks,
        Collection<Long> discussion_languages
) {
}