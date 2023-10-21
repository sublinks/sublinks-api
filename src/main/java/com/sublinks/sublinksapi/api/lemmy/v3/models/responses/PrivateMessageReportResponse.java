package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.views.PrivateMessageReportView;
import lombok.Builder;

@Builder
public record PrivateMessageReportResponse(
        PrivateMessageReportView private_message_report_view
) {
}