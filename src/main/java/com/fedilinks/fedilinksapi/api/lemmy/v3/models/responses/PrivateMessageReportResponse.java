package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PrivateMessageReportView;
import lombok.Builder;

@Builder
public record PrivateMessageReportResponse(
        PrivateMessageReportView private_message_report_view
) {
}