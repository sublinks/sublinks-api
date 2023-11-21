package com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record PrivateMessageReportResponse(
    PrivateMessageReportView private_message_report_view
) {

}