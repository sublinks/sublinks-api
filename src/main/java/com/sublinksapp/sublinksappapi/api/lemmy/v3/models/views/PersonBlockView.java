package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Person;
import lombok.Builder;

@Builder
public record PersonBlockView(
        Person person,
        Person target
) {
}