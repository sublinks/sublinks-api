package com.sublinks.sublinksapi.email.enums;

public enum EmailTemplatesEnum {
  DM_MESSAGE("dm_message"),
  PASSWORD_RESET("password_reset"),
  NEW_REGISTRATION_APPLICATION("new_registration_application"),
  NEW_REPORT("new_report"),
  REGISTRATION_APPROVED("registration_approved"),
  REGISTRATION_SUCCESS("registration_success"),
  VERIFY_EMAIL("verification_email"),
  ;


  private final String template_name;

  EmailTemplatesEnum(String template_name) {

    this.template_name = template_name;
  }


  @Override
  public String toString() {

    return template_name;
  }
}
