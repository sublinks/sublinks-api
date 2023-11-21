package com.sublinks.sublinksapi.api.lemmy.v3.modlog.models;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

@Builder
public record ModAddView(
    ModAdd mod_add,
    Person moderator,
    Person modded_person
) {

}