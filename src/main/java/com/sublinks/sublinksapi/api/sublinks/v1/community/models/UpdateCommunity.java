package com.sublinks.sublinksapi.api.sublinks.v1.community.models;

public record UpdateCommunity(String description,
                              String iconImageUrl,
                              String bannerImageUrl,
                              Boolean isNsfw,
                              Boolean isPostingRestrictedToMods) {

}
