package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentReplyView;
import java.util.List;
import lombok.Builder;

@Builder
public record GetRepliesResponse(
    List<CommentReplyView> replies
) {

}