package com.sublinks.sublinksapi.api.lemmy.v3.privatemessage.models;

import java.util.List;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record ListPrivateMessageReportsResponse(
    List<PrivateMessageReportView> private_message_reports
) {

}