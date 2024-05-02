package com.sublinks.sublinksapi.api.sublinks.v1.community.models;

import java.util.Optional;

public record CreateCommunity(String title,
                              String titleSlug,
                              String description,
                              Optional<String> iconImageUrl,
                              Optional<String> bannerImageUrl,
                              Boolean isNsfw,
                              Boolean isPostingRestrictedToMods) {

}
