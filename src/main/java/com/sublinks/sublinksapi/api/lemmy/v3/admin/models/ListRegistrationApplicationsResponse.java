package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import java.util.List;
import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record ListRegistrationApplicationsResponse(
    List<RegistrationApplicationView> registration_applications
) {

}