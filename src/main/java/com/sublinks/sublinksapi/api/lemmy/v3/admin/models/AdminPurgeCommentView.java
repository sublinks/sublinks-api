package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

/**
 * The AdminPurgeCommentView class represents a view of an admin purge comment action.
 * AdminPurgeCommentView includes the details of the AdminPurgeComment, the admin who performed the
 * action, and the post related to the comment.
 */
@Builder
@SuppressWarnings("RecordComponentName")
public record AdminPurgeCommentView(
    AdminPurgeComment admin_purge_comment,
    Person admin,
    Post post
) {

}