package com.sublinks.sublinksapi.authorization.enums;

public enum RolePermissionCommunityTypes implements RolePermissionInterface {

  // Person permissions
  READ_COMMUNITY("community", AuthorizeAction.READ),
  READ_COMMUNITIES("communities", AuthorizeAction.READ),
  READ_COMMUNITY_MODERATORS("communities-moderator", AuthorizeAction.READ),
  READ_COMMUNITY_AGGREGATION("communities-aggregation", AuthorizeAction.READ),
  CREATE_COMMUNITY("community", AuthorizeAction.CREATE),
  UPDATE_COMMUNITY("community", AuthorizeAction.UPDATE),
  DELETE_COMMUNITY("community", AuthorizeAction.DELETE),
  PURGE_COMMUNITY("community", AuthorizeAction.PURGE),

  // Follow permissions
  COMMUNITY_FOLLOW("community", AuthorizeAction.FOLLOW),
  COMMUNITY_BLOCK("community", AuthorizeAction.BLOCK),

  // Moderator permissions
  MODERATOR_REMOVE_COMMUNITY("community-moderator", AuthorizeAction.DELETE),
  MODERATOR_TRANSFER_COMMUNITY("community-moderator", AuthorizeAction.UPDATE),
  MODERATOR_ADD_MODERATOR("community-moderator", AuthorizeAction.CREATE),
  MODERATOR_REMOVE_MODERATOR("community-moderator-moderator", AuthorizeAction.DELETE),

  // Admin permissions
  ADMIN_SHOW_DELETED_COMMUNITY("community-admin", AuthorizeAction.READ),

  /**
   * Unused
   */
  ADMIN_REMOVE_COMMUNITY("community-admin", AuthorizeAction.REMOVE),
  ADMIN_ADD_COMMUNITY_MODERATOR("community-admin-moderator", AuthorizeAction.CREATE),
  ADMIN_REMOVE_COMMUNITY_MODERATOR("community-admin-moderator", AuthorizeAction.REMOVE),

  /**
   * Unused
   */
  REPORT_COMMUNITY("community-report", AuthorizeAction.CREATE),

  /**
   * Read only for your moderated communities
   */
  REPORT_COMMUNITY_READ("report-community", AuthorizeAction.READ),

  /**
   * Resolve reports for your moderated communities
   */
  REPORT_COMMUNITY_RESOLVE("report-community", AuthorizeAction.UPDATE);

  public final String Entity;
  public final AuthorizeAction Action;

  RolePermissionCommunityTypes(String Entity, AuthorizeAction Action) {

    this.Entity = Entity;
    this.Action = Action;
  }

  @Override
  public String toString() {

    return this.Entity + ":" + this.Action;
  }
}
