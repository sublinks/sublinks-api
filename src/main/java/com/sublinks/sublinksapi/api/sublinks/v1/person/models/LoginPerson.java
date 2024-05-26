package com.sublinks.sublinksapi.api.sublinks.v1.person.models;

import java.util.Optional;
import lombok.Builder;

@Builder
public record LoginPerson(String username,
                          String password,
                          String captcha_token,
                          String captcha_answer) {

}
