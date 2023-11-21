package com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record GetPrivateMessages(
    Boolean unread_only,
    Integer page,
    Integer limit
) {

}