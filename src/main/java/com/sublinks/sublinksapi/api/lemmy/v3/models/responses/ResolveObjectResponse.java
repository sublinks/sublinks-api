package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.PersonView;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.PostView;
import lombok.Builder;

@Builder
public record ResolveObjectResponse(
        CommentView comment,
        PostView post,
        CommunityView community,
        PersonView person
) {
}