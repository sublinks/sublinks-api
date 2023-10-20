package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.ModAdd;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Person;
import lombok.Builder;

@Builder
public record ModAddView(
        ModAdd mod_add,
        Person moderator,
        Person modded_person
) {
}