package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.CommentView;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.CommunityView;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.PersonView;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.PostView;
import lombok.Builder;

@Builder
public record ResolveObjectResponse(
        CommentView comment,
        PostView post,
        CommunityView community,
        PersonView person
) {
}