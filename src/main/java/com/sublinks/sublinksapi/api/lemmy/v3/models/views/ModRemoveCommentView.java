package com.sublinks.sublinksapi.api.lemmy.v3.models.views;

import com.sublinks.sublinksapi.api.lemmy.v3.models.Comment;
import com.sublinks.sublinksapi.api.lemmy.v3.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.models.ModRemoveComment;
import com.sublinks.sublinksapi.api.lemmy.v3.models.Person;
import com.sublinks.sublinksapi.api.lemmy.v3.models.Post;
import lombok.Builder;

@Builder
public record ModRemoveCommentView(
        ModRemoveComment mod_remove_comment,
        Person moderator,
        Comment comment,
        Person commenter,
        Post post,
        Community community
) {
}