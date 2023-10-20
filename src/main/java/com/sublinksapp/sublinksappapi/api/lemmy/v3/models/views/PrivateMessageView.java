package com.sublinksapp.sublinksappapi.api.lemmy.v3.models.views;

import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.Person;
import com.sublinksapp.sublinksappapi.api.lemmy.v3.models.PrivateMessage;
import lombok.Builder;

@Builder
public record PrivateMessageView(
        PrivateMessage private_message,
        Person creator,
        Person recipient
) {
}