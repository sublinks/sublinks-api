package com.sublinks.sublinksapi.comment.models;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.enums.CommentSortType;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.post.dto.Post;
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
