package com.sublinks.sublinksapi.authorization.enums;

/**
 * The RolePermissionMediaTypes class represents the media types that a role permission can be
 * associated with.
 */
public enum RolePermissionMediaTypes implements RolePermissionInterface {

  CREATE_MEDIA("media", AuthorizeAction.CREATE);

  public final String entity;
  public final AuthorizeAction action;

  RolePermissionMediaTypes(String entity, AuthorizeAction action) {

    this.entity = entity;
    this.action = action;
  }

  @Override
  public String toString() {

    return this.entity + ":" + this.action;
  }
}
