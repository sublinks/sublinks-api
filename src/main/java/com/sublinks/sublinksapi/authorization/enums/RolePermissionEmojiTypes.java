package com.sublinks.sublinksapi.authorization.enums;

public enum RolePermissionEmojiTypes implements RolePermissionInterface {

  // Person permissions
  CREATE_EMOJI("emoji", AuthorizeAction.CREATE),
  UPDATE_EMOJI("emoji", AuthorizeAction.UPDATE),
  DELETE_EMOJI("emoji", AuthorizeAction.DELETE);

  public final String Entity;
  public final AuthorizeAction Action;

  RolePermissionEmojiTypes(String Entity, AuthorizeAction Action) {

    this.Entity = Entity;
    this.Action = Action;
  }

  @Override
  public String toString() {

    return this.Entity + ":" + this.Action;
  }
}
