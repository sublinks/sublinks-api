package com.sublinks.sublinksapi.federation.models;

import lombok.Builder;

@Builder
public record Actor(
    String id,
    String actor_type,
    String name,
    String username,
    String bio,
    String matrix_user_id,
    String private_key,
    String public_key
) {

}