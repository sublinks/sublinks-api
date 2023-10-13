package com.fedilinks.fedilinksapi.api.lemmy.v3.models.views;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.AdminPurgeComment;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Person;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Post;
import lombok.Builder;

@Builder
public record AdminPurgeCommentView(
        AdminPurgeComment admin_purge_comment,
        Person admin,
        Post post
) {
}