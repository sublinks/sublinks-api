package com.sublinks.sublinksapi.api.lemmy.v3.search.models;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentView;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.CommunityView;
import com.sublinks.sublinksapi.api.lemmy.v3.enums.SearchType;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonView;
import lombok.Builder;

import java.util.List;

@Builder
public record SearchResponse(
        SearchType type_,
        List<CommentView> comments,
        List<PostView> posts,
        List<CommunityView> communities,
        List<PersonView> users
) {
}