package com.sublinks.sublinksapi.api.lemmy.v3.privateMessage.models;

import java.util.List;
import lombok.Builder;

@Builder
public record ListPrivateMessageReportsResponse(
    List<PrivateMessageReportView> private_message_reports
) {

}