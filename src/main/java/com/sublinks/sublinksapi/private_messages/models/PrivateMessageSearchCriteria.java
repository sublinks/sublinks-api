package com.sublinks.sublinksapi.private_messages.models;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.enums.CommentSortType;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.private_messages.enums.PrivateMessageSortType;
import lombok.Builder;

@Builder
public record PrivateMessageSearchCriteria(
        PrivateMessageSortType commentSortType,
        int perPage,
        int page,
        boolean unresolvedOnly
) {

}
