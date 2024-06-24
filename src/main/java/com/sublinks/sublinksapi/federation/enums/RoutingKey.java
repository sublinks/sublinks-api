package com.sublinks.sublinksapi.federation.enums;

public enum RoutingKey {
  ACTOR_CREATE("actor.create"),
  POST_CREATE("post.create"),
  COMMENT_CREATE("comment.create");

  private final String value;

  RoutingKey(String value) {

    this.value = value;
  }

  public String getValue() {

    return value;
  }
}
