package com.sublinks.sublinksapi.api.sublinks.v1.person.models;

import com.sublinks.sublinksapi.api.sublinks.v1.roles.models.Role;
import java.util.List;
import lombok.Builder;

@Builder
public record CreatePerson(String name,
                           String displayName,
                           Role role,
                           List<Langauges> languages,
                           String createdAt,
                           String updatedAt) {

}
