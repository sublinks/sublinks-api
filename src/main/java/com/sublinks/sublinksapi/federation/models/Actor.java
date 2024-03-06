package com.sublinks.sublinksapi.federation.models;

import lombok.Builder;

@Builder
public record Actor(
  String actor_id,
  String actor_type,
  String display_name,
  String bio,
  String matrix_user_id,
  String private_key,
  String public_key
) {
}