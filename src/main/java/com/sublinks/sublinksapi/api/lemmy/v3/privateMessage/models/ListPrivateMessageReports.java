package com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models;

import lombok.Builder;

@Builder
public record ListPrivateMessageReports(
    Integer page,
    Integer limit,
    Boolean unresolved_only
) {

}