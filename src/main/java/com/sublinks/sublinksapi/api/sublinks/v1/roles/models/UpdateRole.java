package com.sublinks.sublinksapi.api.sublinks.v1.roles.models;

import java.util.List;

public record UpdateRole(
    String name,
    String description,
    Boolean active,
    List<String> permissions) {

}
