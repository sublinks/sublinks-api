package com.sublinks.sublinksapi.person.models;

import com.sublinks.sublinksapi.api.lemmy.v3.enums.CommentSortType;
import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.post.dto.Post;
import lombok.Builder;

@Builder
public record PersonMentionSearchCriteria(
        CommentSortType sort,
        int page,
        int perPage,
        Boolean unreadOnly
) {

}
