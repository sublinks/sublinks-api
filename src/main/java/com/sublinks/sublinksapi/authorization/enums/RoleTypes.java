package com.sublinks.sublinksapi.authorization.enums;

public enum RoleTypes {
  ADMIN("admin"),
  BANNED("banned"),
  GUEST("guest"),
  REGISTERED("registered");

  public final String Label;

  RoleTypes(String Label) {

    this.Label = Label;
  }
}
