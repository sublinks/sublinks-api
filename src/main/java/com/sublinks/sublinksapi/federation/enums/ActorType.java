package com.sublinks.sublinksapi.federation.enums;

public enum ActorType {
  USER("Person"),
  COMMUNITY("Group");

  private final String value;

  ActorType(String value) {

    this.value = value;
  }

  public String getValue() {

    return value;
  }
}
