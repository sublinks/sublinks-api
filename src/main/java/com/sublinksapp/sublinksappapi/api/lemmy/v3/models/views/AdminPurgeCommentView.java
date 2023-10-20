package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.AdminPurgeComment;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Person;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Post;
import lombok.Builder;

@Builder
public record AdminPurgeCommentView(
        AdminPurgeComment admin_purge_comment,
        Person admin,
        Post post
) {
}