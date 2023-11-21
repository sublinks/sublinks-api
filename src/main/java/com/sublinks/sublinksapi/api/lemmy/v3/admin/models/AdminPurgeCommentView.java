package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import com.sublinks.sublinksapi.api.lemmy.v3.post.models.Post;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record AdminPurgeCommentView(
    AdminPurgeComment admin_purge_comment,
    Person admin,
    Post post
) {

}