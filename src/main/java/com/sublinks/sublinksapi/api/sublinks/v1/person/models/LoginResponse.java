package com.sublinks.sublinksapi.api.sublinks.v1.person.models;

import java.util.Optional;
import lombok.Builder;

@Builder
public record LoginResponse(Optional<String> token,
                            RegistrationState status,
                            Optional<String> error) {

}
