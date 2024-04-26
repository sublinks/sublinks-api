package com.sublinks.sublinksapi.api.lemmy.v3.post.models;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.VoteView;
import java.util.List;
import lombok.Builder;

@Builder
public record ListPostLikesResponse(
    List<VoteView> post_likes
) {

}