package com.sublinks.sublinksapi.api.sublinks.v1.roles.models;

import java.util.List;

public record CreateRole(
    String name,
    String description,
    Boolean active,
    List<String> permissions) {

}
