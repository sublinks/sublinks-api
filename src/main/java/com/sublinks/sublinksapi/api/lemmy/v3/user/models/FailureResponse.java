package com.sublinks.sublinksapi.api.lemmy.v3.user.models;

import lombok.Builder;

@Builder
@SuppressWarnings("RecordComponentName")
public record FailureResponse(
    String error

) {


}