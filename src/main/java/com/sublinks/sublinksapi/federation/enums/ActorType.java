package com.sublinks.sublinksapi.federation.enums;

/**
 * The ActorType enum represents the types of actors in the system.
 */
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
