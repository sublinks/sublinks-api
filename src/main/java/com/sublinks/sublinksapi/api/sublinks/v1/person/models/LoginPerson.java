package com.sublinks.sublinksapi.api.sublinks.v1.person.models;

import java.util.Optional;
import lombok.Builder;

@Builder
public record LoginPerson(String username,
                          String password,
                          Optional<String> captcha_token,
                          Optional<String> captcha_answer) {

}
