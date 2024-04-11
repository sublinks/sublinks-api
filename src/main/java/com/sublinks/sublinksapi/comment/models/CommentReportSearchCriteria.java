package com.sublinks.sublinksapi.comment.models;

import com.sublinks.sublinksapi.community.entities.Community;
import lombok.Builder;
import java.util.List;

@Builder
public record CommentReportSearchCriteria(
    int perPage,
    int page,
    List<Community> community,
    boolean unresolvedOnly
) {

}
