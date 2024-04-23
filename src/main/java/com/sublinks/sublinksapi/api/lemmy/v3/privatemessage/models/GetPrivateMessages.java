package com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models;

import lombok.Builder;
import java.util.Optional;

@Builder
@SuppressWarnings("RecordComponentName")
public record GetPrivateMessages(
    Integer page,
    Integer limit,
    Long creator_id,
    Optional<Boolean> unread_only
) {

}