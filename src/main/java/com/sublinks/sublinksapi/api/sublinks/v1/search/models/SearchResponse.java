package com.sublinks.sublinksapi.api.sublinks.v1.search.models;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import java.util.List;
import lombok.Builder;

// @todo: Add Communities, Posts, Comments, and Messages
@Builder
public record SearchResponse(String key,
                             List<Person> persons) {

}
