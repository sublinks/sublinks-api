package com.sublinks.sublinksapi.api.lemmy.v3.models;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommunityBlockView;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommunityFollowerView;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.LocalUserView;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.PersonBlockView;
import lombok.Builder;

import java.util.Collection;

@Builder
public record MyUserInfo(
        LocalUserView local_user_view,
        Collection<CommunityFollowerView> follows,
        Collection<CommunityModeratorView> moderates,
        Collection<CommunityBlockView> community_blocks,
        Collection<PersonBlockView> person_blocks,
        Collection<Integer> discussion_languages
) {
}