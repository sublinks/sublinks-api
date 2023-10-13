package com.fedilinks.fedilinksapi.api.lemmy.v3.models.views;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.ModBan;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Person;
import lombok.Builder;

@Builder
public record ModBanView(
        ModBan mod_ban,
        Person moderator,
        Person banned_person
) {
}