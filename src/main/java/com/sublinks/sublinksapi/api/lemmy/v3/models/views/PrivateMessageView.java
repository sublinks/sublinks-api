package com.sublinks.sublinksapi.api.lemmy.v3.models.views;

import com.sublinks.sublinksapi.api.lemmy.v3.models.Person;
import com.sublinks.sublinksapi.api.lemmy.v3.models.PrivateMessage;
import lombok.Builder;

@Builder
public record PrivateMessageView(
        PrivateMessage private_message,
        Person creator,
        Person recipient
) {
}