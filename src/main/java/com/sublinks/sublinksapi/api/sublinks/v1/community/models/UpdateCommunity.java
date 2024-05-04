package com.sublinks.sublinksapi.api.sublinks.v1.community.models;

import java.util.Optional;

public record UpdateCommunity(Optional<String> title,
                              Optional<String> description,
                              Optional<String> iconImageUrl,
                              Optional<String> bannerImageUrl,
                              Optional<Boolean> isNsfw,
                              Optional<Boolean> deleted,
                              Optional<Boolean> removed,
                              Optional<Boolean> isPostingRestrictedToMods) {

}
