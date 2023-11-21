package com.sublinks.sublinksapi.api.lemmy.v3.resolveobject.models;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonView;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record ResolveObjectResponse(
    CommentView comment,
    PostView post,
    CommunityView community,
    PersonView person
) {

}