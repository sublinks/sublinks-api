package com.sublinks.sublinksapi.email.models;

import lombok.Builder;

@Builder
public record CreateEmailEvent(
    CreateEmailRequest createEmailRequest
) {


}
