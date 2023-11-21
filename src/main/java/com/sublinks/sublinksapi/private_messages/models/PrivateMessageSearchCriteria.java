package com.sublinks.sublinksapi.private_messages.models;

import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.private_messages.enums.PrivateMessageSortType;
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
