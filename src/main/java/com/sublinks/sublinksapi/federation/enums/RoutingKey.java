package com.sublinks.sublinksapi.federation.enums;

public enum RoutingKey {
  ACTORCREATED("actor.created");

  private final String value;

  RoutingKey(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
