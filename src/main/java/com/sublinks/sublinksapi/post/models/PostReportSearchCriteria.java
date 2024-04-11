package com.sublinks.sublinksapi.post.models;

import com.sublinks.sublinksapi.community.entities.Community;
import java.util.List;
import lombok.Builder;

@Builder
public record PostReportSearchCriteria(
    int perPage,
    int page,
    List<Community> community,
    boolean unresolvedOnly
) {

}
