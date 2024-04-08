package com.sublinks.sublinksapi.email.models;

import com.sublinks.sublinksapi.email.dto.Email;
import lombok.Builder;

@Builder
public record CreateEmailEvent(
    Email email
) {


}
