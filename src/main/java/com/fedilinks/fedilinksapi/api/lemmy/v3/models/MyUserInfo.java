package com.fedilinks.fedilinksapi.api.lemmy.v3.models;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommunityBlockView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommunityFollowerView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommunityModeratorView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.LocalUserView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PersonBlockView;
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