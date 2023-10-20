package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.responses;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views.CommentView;
import lombok.Builder;

import java.util.List;

@Builder
public record GetCommentsResponse(
        List<CommentView> comments
) {
}