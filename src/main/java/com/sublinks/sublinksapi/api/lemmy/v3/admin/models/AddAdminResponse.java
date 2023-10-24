package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonView;
import lombok.Builder;

import java.util.List;

@Builder
public record AddAdminResponse(
        List<PersonView> admins
) {
}