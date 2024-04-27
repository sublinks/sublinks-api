package com.sublinks.sublinksapi.authorization.enums;

public enum RolePermissionPrivateMessageTypes implements RolePermissionInterface {

  // Person permissions
  READ_PRIVATE_MESSAGES("message", AuthorizeAction.READ),
  MARK_PRIVATE_MESSAGE_AS_READ("message-read", AuthorizeAction.UPDATE),
  CREATE_PRIVATE_MESSAGE("message", AuthorizeAction.CREATE),
  UPDATE_PRIVATE_MESSAGE("message", AuthorizeAction.UPDATE),
  DELETE_PRIVATE_MESSAGE("message", AuthorizeAction.DELETE),
  PURGE_PRIVATE_MESSAGE("message", AuthorizeAction.PURGE),

  // Report permissions
  REPORT_PRIVATE_MESSAGE("message-report", AuthorizeAction.CREATE);

  public final String Entity;
  public final AuthorizeAction Action;

  RolePermissionPrivateMessageTypes(String Entity, AuthorizeAction Action) {

    this.Entity = Entity;
    this.Action = Action;
  }

  @Override
  public String toString() {

    return this.Entity + ":" + this.Action;
  }
}
