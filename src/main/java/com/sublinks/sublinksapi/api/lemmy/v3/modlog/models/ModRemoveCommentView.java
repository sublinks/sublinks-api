package com.sublinks.sublinksapi.api.lemmy.v3.modlog.models;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.Comment;
import com.sublinks.sublinksapi.api.lemmy.v3.community.models.Community;
import com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
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