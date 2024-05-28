package com.sublinks.sublinksapi.api.sublinks.v1.person.models;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Builder
public record LoginPerson(
    String username,
    String password,
    String captcha_token,
    String captcha_answer) {

  public LoginPerson {

    if (username == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username_required");
    }
    if (password == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password_required");
    }
  }

}
