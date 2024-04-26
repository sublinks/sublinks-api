package com.sublinks.sublinksapi.comment.models;

import com.sublinks.sublinksapi.community.entities.Community;
import java.util.List;
import lombok.Builder;

@Builder
public record CommentReportSearchCriteria(
    int perPage,
    int page,
    List<Community> community,
    boolean unresolvedOnly
) {

}
