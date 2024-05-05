package com.sublinks.sublinksapi.authorization.enums;

public enum RolePermissionModLogTypes implements RolePermissionInterface {

  // Person permissions
  READ_MODLOG("modlog", AuthorizeAction.READ);

  public final String Entity;
  public final AuthorizeAction Action;

  RolePermissionModLogTypes(String Entity, AuthorizeAction Action) {

    this.Entity = Entity;
    this.Action = Action;
  }

  @Override
  public String toString() {

    return this.Entity + ":" + this.Action;
  }
}
