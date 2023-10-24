package com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models;

import lombok.Builder;

@Builder
public record PrivateMessageReportResponse(
        PrivateMessageReportView private_message_report_view
) {
}