package com.sublinks.sublinksapi.api.sublinks.v1.person.models;

import java.util.List;
import java.util.Optional;
import lombok.Builder;

@Builder
public record UpdatePerson(Optional<String> displayName,
                           Optional<String> email,
                           Optional<List<String>> languagesKeys,
                           Optional<String> avatarImageUrl,
                           Optional<String> bannerImageUrl,
                           Optional<String> bio,
                           Optional<String> matrixUserId,
                           Optional<String> oldPassword,
                           Optional<String> password,
                           Optional<String> passwordConfirmation) {

}
