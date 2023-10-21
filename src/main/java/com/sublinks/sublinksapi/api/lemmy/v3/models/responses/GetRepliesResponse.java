package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CommentReplyView;
import lombok.Builder;

import java.util.List;

@Builder
public record GetRepliesResponse(
        List<CommentReplyView> replies
) {
}