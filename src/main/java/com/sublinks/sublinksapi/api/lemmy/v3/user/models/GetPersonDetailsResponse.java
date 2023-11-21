package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityModeratorView;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostView;
import java.util.Collection;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record GetPersonDetailsResponse(
    PersonView person_view,
    Collection<CommentView> comments,
    Collection<PostView> posts,
    Collection<CommunityModeratorView> moderates
) {

}