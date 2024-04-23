package com.sublinks.sublinksapi.federation.enums;

public enum RoutingKey {
  ACTOR_CREATE("actor.create");

  private final String value;

  RoutingKey(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
