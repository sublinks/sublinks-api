package com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record PrivateMessageReportView(
    PrivateMessageReport private_message_report,
    PrivateMessage private_message,
    Person private_message_creator,
    Person creator,
    Person resolver
) {

}