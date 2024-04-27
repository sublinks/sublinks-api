package com.sublinks.sublinksapi.authorization.enums;

public enum RolePermissionRegistrationApplicationTypes implements RolePermissionInterface {

  // Person permissions
  REGISTRATION_APPLICATION_READ("registration-application", AuthorizeAction.READ),
  REGISTRATION_APPLICATION_UPDATE("registration-application", AuthorizeAction.UPDATE);

  public final String Entity;
  public final AuthorizeAction Action;

  RolePermissionRegistrationApplicationTypes(String Entity, AuthorizeAction Action) {

    this.Entity = Entity;
    this.Action = Action;
  }

  @Override
  public String toString() {

    return this.Entity + ":" + this.Action;
  }
}
