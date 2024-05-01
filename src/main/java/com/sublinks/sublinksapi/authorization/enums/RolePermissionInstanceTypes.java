package com.sublinks.sublinksapi.authorization.enums;

/**
 * The RolePermissionInstanceTypes enum represents the types of role permissions for specific
 * instances. Each RolePermissionInstanceTypes contains an Entity and an Action.
 */
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

  public final String entity;
  public final AuthorizeAction action;

  RolePermissionInstanceTypes(String entity, AuthorizeAction action) {

    this.entity = entity;
    this.action = action;
  }

  @Override
  public String toString() {

    return this.entity + ":" + this.action;
  }
}
