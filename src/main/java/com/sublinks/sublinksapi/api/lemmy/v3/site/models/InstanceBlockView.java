package com.sublinks.sublinksapi.api.lemmy.v3.site.models;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record InstanceBlockView(
    Person person,
    Instance instance,
    Site site
) {

}