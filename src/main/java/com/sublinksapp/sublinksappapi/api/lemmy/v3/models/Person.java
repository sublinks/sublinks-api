package com.sublinksapp.sublinksappapi.api.lemmy.v3.models;

import lombok.Builder;

@Builder
public record Person(
        Long id,
        String name,
        String display_name,
        String avatar,
        boolean banned,
        String published,
        String updated,
        String actor_id,
        String bio,
        boolean local,
        String banner,
        boolean deleted,
        String inbox_url,
        String shared_inbox_url,
        String matrix_user_id,
        boolean admin,
        boolean bot_account,
        String ban_expires,
        Long instance_id
) {
}