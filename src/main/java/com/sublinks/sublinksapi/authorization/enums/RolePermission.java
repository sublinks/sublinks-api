package com.sublinks.sublinksapi.authorization.enums;

public enum RolePermission {

  // User permissions
  // Post permissions
  READ_POST("post", AuthorizeAction.READ),
  CREATE_POST("post", AuthorizeAction.CREATE),
  UPDATE_POST("post", AuthorizeAction.UPDATE),
  DELETE_POST("post", AuthorizeAction.DELETE),
  REMOVE_POST("post", AuthorizeAction.REMOVE),
  PURGE_POST("post", AuthorizeAction.PURGE),
  FAVORITE_POST("post", AuthorizeAction.FAVORITE),

  // Comment permissions
  READ_COMMENT("comment", AuthorizeAction.READ),
  MARK_COMMENT_AS_READ("comment-read", AuthorizeAction.UPDATE),
  CREATE_COMMENT("comment", AuthorizeAction.CREATE),
  UPDATE_COMMENT("comment", AuthorizeAction.UPDATE),
  DELETE_COMMENT("comment", AuthorizeAction.DELETE),
  REMOVE_COMMENT("comment", AuthorizeAction.REMOVE),
  PURGE_COMMENT("comment", AuthorizeAction.PURGE),
  FAVORITE_COMMENT("comment", AuthorizeAction.FAVORITE),

  // Private message permissions
  READ_PRIVATE_MESSAGE("message", AuthorizeAction.READ),
  CREATE_PRIVATE_MESSAGE("message", AuthorizeAction.CREATE),
  UPDATE_PRIVATE_MESSAGE("message", AuthorizeAction.UPDATE),
  DELETE_PRIVATE_MESSAGE("message", AuthorizeAction.DELETE),
  PURGE_PRIVATE_MESSAGE("message", AuthorizeAction.PURGE),

  // Community permissions
  READ_COMMUNITY("community", AuthorizeAction.READ),
  READ_COMMUNITIES("communities", AuthorizeAction.READ),
  CREATE_COMMUNITY("community", AuthorizeAction.CREATE),
  UPDATE_COMMUNITY("community", AuthorizeAction.UPDATE),
  DELETE_COMMUNITY("community", AuthorizeAction.DELETE),
  PURGE_COMMUNITY("community", AuthorizeAction.PURGE),

  // User permissions
  READ_USER("user", AuthorizeAction.READ),
  UPDATE_USER("user", AuthorizeAction.UPDATE),
  DELETE_USER("user", AuthorizeAction.DELETE),
  PURGE_USER("user", AuthorizeAction.PURGE),

  // Follow permissions
  COMMUNITY_FOLLOW("community", AuthorizeAction.FOLLOW),
  COMMUNITY_BLOCK("community", AuthorizeAction.BLOCK),

  USER_BLOCK("user", AuthorizeAction.BLOCK),

  // Vote permissions
  POST_UPVOTE("post-upvote", AuthorizeAction.CREATE),
  POST_DOWNVOTE("post-downvote", AuthorizeAction.CREATE),
  POST_NEUTRAL("post-neutral", AuthorizeAction.CREATE),

  COMMENT_UPVOTE("comment-upvote", AuthorizeAction.CREATE),
  COMMENT_DOWNVOTE("comment-downvote", AuthorizeAction.CREATE),
  COMMENT_NEUTRAL("comment-neutral", AuthorizeAction.CREATE),

  // Emoji permissions

  CREATE_EMOJI("emoji", AuthorizeAction.CREATE),
  UPDATE_EMOJI("emoji", AuthorizeAction.UPDATE),
  DELETE_EMOJI("emoji", AuthorizeAction.DELETE),

  // Moderator permissions
  MODERATOR_REMOVE_POST("post-moderator", AuthorizeAction.DELETE),
  MODERATOR_REMOVE_COMMENT("comment-moderator", AuthorizeAction.DELETE),
  MODERATOR_REMOVE_COMMUNITY("community-moderator", AuthorizeAction.DELETE),
  MODERATOR_TRANSFER_COMMUNITY("community-moderator", AuthorizeAction.UPDATE),
  MODERATOR_BAN_USER("user-moderator", AuthorizeAction.BAN),
  MODERATOR_PIN_POST("post-moderator", AuthorizeAction.UPDATE),
  MODERATOR_PIN_COMMENT("comment-moderator", AuthorizeAction.UPDATE),
  MODERATOR_PURGE_POST("post-moderator", AuthorizeAction.PURGE),
  MODERATOR_PURGE_COMMENT("comment-moderator", AuthorizeAction.PURGE),
  MODERATOR_ADD_MODERATOR("community-moderator", AuthorizeAction.CREATE),
  MODERATOR_REMOVE_MODERATOR("community-moderator", AuthorizeAction.DELETE),
  MODERATOR_SHOW_DELETED_POST("post-moderator", AuthorizeAction.READ),
  MODERATOR_SHOW_DELETED_COMMENT("comment-moderator", AuthorizeAction.READ),
  MODERATOR_SPEAK("moderator-speak", AuthorizeAction.CREATE),

  // Admin permissions
  ADMIN_SHOW_DELETED_POST("post-admin", AuthorizeAction.READ),
  ADMIN_SHOW_DELETED_COMMENT("comment-admin", AuthorizeAction.READ),
  ADMIN_SHOW_DELETED_COMMUNITY("community-admin", AuthorizeAction.READ),
  ADMIN_SPEAK("admin-speak", AuthorizeAction.CREATE),
  ADMIN_PIN_POST("post-admin-pin", AuthorizeAction.UPDATE),

  /**
   * Unused
   */
  ADMIN_PIN_COMMENT("comment-admin-pin", AuthorizeAction.UPDATE),
  ADMIN_REMOVE_USER("user-admin", AuthorizeAction.REMOVE),
  ADMIN_REMOVE_COMMUNITY("community-admin", AuthorizeAction.REMOVE),
  ADMIN_ADD_COMMUNITY_MODERATOR("community-admin-moderator", AuthorizeAction.CREATE),
  ADMIN_REMOVE_COMMUNITY_MODERATOR("community-admin-moderator", AuthorizeAction.REMOVE),

  // Registration Application permissions

  REGISTRATION_APPLICATION_READ("registration-application", AuthorizeAction.READ),
  REGISTRATION_APPLICATION_UPDATE("registration-application", AuthorizeAction.UPDATE),

  // Instance permissions
  INSTANCE_UPDATE_SETTINGS("instance", AuthorizeAction.UPDATE),

  /**
   * Unused
   */
  INSTANCE_CREATE_ROLE("instance-roles", AuthorizeAction.CREATE),

  /**
   * Unused
   */
  INSTANCE_DELETE_ROLE("instance-roles", AuthorizeAction.DELETE),

  /**
   * Unused
   */
  INSTANCE_UPDATE_ROLES("instance-roles", AuthorizeAction.UPDATE),
  INSTANCE_DEFEDERATE_INSTANCE("instance-federation", AuthorizeAction.BLOCK),
  INSTANCE_FEDERATE_INSTANCE("instance-federation", AuthorizeAction.FOLLOW),
  INSTANCE_ADD_ADMIN("instance-admin", AuthorizeAction.CREATE),
  INSTANCE_REMOVE_ADMIN("instance-admin", AuthorizeAction.DELETE),


  // Report permissions
  REPORT_POST("post-report", AuthorizeAction.CREATE),
  REPORT_COMMENT("comment-report", AuthorizeAction.CREATE),
  REPORT_PRIVATE_MESSAGE("message-report", AuthorizeAction.CREATE),

  /**
   * Unused
   */
  REPORT_USER("user-report", AuthorizeAction.CREATE),

  /**
   * Unused
   */
  REPORT_COMMUNITY("community-report", AuthorizeAction.CREATE),

  /**
   * Read only for your moderated communities
   */
  REPORT_COMMUNITY_READ("report-community", AuthorizeAction.READ),
  /**
   * Read only for every community
   */
  REPORT_INSTANCE_READ("report-instance", AuthorizeAction.READ),

  /**
   * Resolve reports for your moderated communities
   */
  REPORT_COMMUNITY_RESOLVE("report-community", AuthorizeAction.UPDATE),
  /**
   * Resolve reports for every community
   */
  REPORT_INSTANCE_RESOLVE("report-instance", AuthorizeAction.UPDATE),

  /**
   * Admin = Has all permissions
   */
  ADMIN("admin", AuthorizeAction.READ),

  IGNORE_BLOCK("admin-ignore", AuthorizeAction.READ),

  /**
   * The role who has this permission is the default role unreigstered users and for users if there is no "registered" role.
   */
  DEFAULT("default", AuthorizeAction.READ),

  /**
   * The role who has this permission is banned from the instance
   */
  BANNED("banned", AuthorizeAction.READ),

  /**
   * The role who has this permission is registered on the instance
   */
  REGISTERED("registered", AuthorizeAction.READ);

  public final String Entity;
  public final AuthorizeAction Action;

  RolePermission(String Entity, AuthorizeAction Action) {

    this.Entity = Entity;
    this.Action = Action;
  }

  @Override
  public String toString() {

    return this.Entity + ":" + this.Action;
  }
}
