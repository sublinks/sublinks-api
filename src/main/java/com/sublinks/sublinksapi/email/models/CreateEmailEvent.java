package com.sublinks.sublinksapi.email.models;

import com.sublinks.sublinksapi.email.entities.Email;
import lombok.Builder;

@Builder
public record CreateEmailEvent(
    Email email
) {


}
