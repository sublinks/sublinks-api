package com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record PrivateMessageView(
    PrivateMessage private_message,
    Person creator,
    Person recipient
) {

}