package com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

@Builder
public record PrivateMessageView(
    PrivateMessage private_message,
    Person creator,
    Person recipient
) {

}