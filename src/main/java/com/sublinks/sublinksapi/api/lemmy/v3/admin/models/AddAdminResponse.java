package com.sublinks.sublinksapi.api.lemmy.v3.admin.models;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonView;
import java.util.Collection;
import lombok.Builder;

@Builder
public record AddAdminResponse(
    Collection<PersonView> admins
) {

}
