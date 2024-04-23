package com.sublinks.sublinksapi.privatemessages.models;

import lombok.Builder;

@Builder
public record PrivateMessageReportSearchCriteria(
    int perPage,
    int page,
    boolean unresolvedOnly
) {

}
