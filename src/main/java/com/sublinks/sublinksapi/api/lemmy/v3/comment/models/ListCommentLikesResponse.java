package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import lombok.Builder;
import java.util.List;

@Builder
public record ListCommentLikesResponse(
    List<VoteView> comment_likes
) {

}