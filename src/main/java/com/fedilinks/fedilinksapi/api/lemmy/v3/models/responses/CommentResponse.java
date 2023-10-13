package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CommentView;
import lombok.Builder;

import java.util.List;

@Builder
public record CommentResponse(
        CommentView comment_view,
        List<Long> recipient_ids,
        String form_id
) {
}