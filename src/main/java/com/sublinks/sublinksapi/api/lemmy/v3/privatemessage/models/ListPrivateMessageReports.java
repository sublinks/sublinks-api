package com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record ListPrivateMessageReports(
    Integer page,
    Integer limit,
    Boolean unresolved_only
) {

}