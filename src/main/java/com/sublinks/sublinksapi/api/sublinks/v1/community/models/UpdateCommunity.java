package com.sublinks.sublinksapi.api.sublinks.v1.community.models;

import java.util.Optional;

public record UpdateCommunity(String title,
                              String description,
                              Optional<String> iconImageUrl,
                              Optional<String> bannerImageUrl,
                              Boolean isNsfw,
                              Optional<Boolean> deleted,
                              Optional<Boolean> removed,
                              Boolean isPostingRestrictedToMods) {

}
