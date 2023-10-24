package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReplyView;
import lombok.Builder;

import java.util.List;

@Builder
public record GetRepliesResponse(
        List<CommentReplyView> replies
) {
}