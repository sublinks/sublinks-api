package com.sublinks.sublinksapi.authorization.enums;

public enum RolePermissionInstanceTypes implements RolePermissionInterface {

  // Person permissions
  INSTANCE_UPDATE_SETTINGS("instance", AuthorizeAction.UPDATE),
  INSTANCE_BAN_USER("user-admin", AuthorizeAction.BAN),
  INSTANCE_BAN_READ("user-admin-ban", AuthorizeAction.READ),
  INSTANCE_CREATE_ROLE("instance-roles", AuthorizeAction.CREATE),
  INSTANCE_DELETE_ROLE("instance-roles", AuthorizeAction.DELETE),
  INSTANCE_UPDATE_ROLES("instance-roles", AuthorizeAction.UPDATE),
  INSTANCE_DEFEDERATE_INSTANCE("instance-federation", AuthorizeAction.BLOCK),
  INSTANCE_FEDERATE_INSTANCE("instance-federation", AuthorizeAction.FOLLOW),
  INSTANCE_ADD_ADMIN("instance-admin", AuthorizeAction.CREATE),
  INSTANCE_REMOVE_ADMIN("instance-admin", AuthorizeAction.DELETE),
  INSTANCE_SEARCH("instance-search", AuthorizeAction.READ),
  REPORT_INSTANCE_READ("report-instance", AuthorizeAction.READ),
  REPORT_INSTANCE_RESOLVE("report-instance", AuthorizeAction.UPDATE);

  public final String Entity;
  public final AuthorizeAction Action;

  RolePermissionInstanceTypes(String Entity, AuthorizeAction Action) {

    this.Entity = Entity;
    this.Action = Action;
  }

  @Override
  public String toString() {

    return this.Entity + ":" + this.Action;
  }
}
