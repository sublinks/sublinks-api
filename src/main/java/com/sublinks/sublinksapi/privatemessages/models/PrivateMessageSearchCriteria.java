package com.sublinks.sublinksapi.privatemessages.models;

import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.privatemessages.enums.PrivateMessageSortType;
import lombok.Builder;

@Builder
public record PrivateMessageSearchCriteria(
    PrivateMessageSortType commentSortType,
    int perPage,
    int page,
    boolean unresolvedOnly,
    Person person
) {

}
