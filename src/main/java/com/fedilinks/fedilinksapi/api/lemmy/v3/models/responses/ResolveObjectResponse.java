package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommentView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommunityView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PersonView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PostView;
import lombok.Builder;

@Builder
public record ResolveObjectResponse(
        CommentView comment,
        PostView post,
        CommunityView community,
        PersonView person
) {
}