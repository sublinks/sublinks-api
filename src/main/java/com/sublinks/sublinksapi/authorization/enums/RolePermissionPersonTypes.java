package com.sublinks.sublinksapi.authorization.enums;

public enum RolePermissionPersonTypes implements RolePermissionInterface {

  // Person permissions
  READ_USER("user", AuthorizeAction.READ),
  UPDATE_USER("user", AuthorizeAction.UPDATE),
  UPDATE_USER_SETTINGS("user-settings", AuthorizeAction.UPDATE),
  DELETE_USER("user", AuthorizeAction.DELETE),
  PURGE_USER("user", AuthorizeAction.PURGE),
  READ_MENTION_USER("user-mention", AuthorizeAction.READ),
  MARK_MENTION_AS_READ("user-mention-read", AuthorizeAction.UPDATE),
  READ_REPLIES("user-reply-read", AuthorizeAction.READ),
  MARK_REPLIES_AS_READ("user-reply-read", AuthorizeAction.UPDATE),
  RESET_PASSWORD("user-reset-password", AuthorizeAction.DELETE),
  USER_BLOCK("user", AuthorizeAction.BLOCK),

  // Moderator permissions
  MODERATOR_BAN_USER("user-moderator", AuthorizeAction.BAN),

  /**
   * Unused
   */
  ADMIN_REMOVE_USER("user-admin", AuthorizeAction.REMOVE),
  REPORT_USER("user-report", AuthorizeAction.CREATE);

  public final String Entity;
  public final AuthorizeAction Action;

  RolePermissionPersonTypes(String Entity, AuthorizeAction Action) {

    this.Entity = Entity;
    this.Action = Action;
  }

  @Override
  public String toString() {

    return this.Entity + ":" + this.Action;
  }
}
