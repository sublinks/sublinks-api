package com.sublinks.sublinksapi.api.sublinks.v1.community.models;

import com.sublinks.sublinksapi.api.sublinks.v1.languages.models.LanguageResponse;
import java.util.List;
import java.util.Optional;
import lombok.Builder;

@Builder
public record CommunityResponse(String key,
                                String title,
                                String titleSlug,
                                String description,
                                String iconImageUrl,
                                Optional<String> bannerImageUrl,
                                String activityPubId,
                                List<LanguageResponse> languages,
                                Boolean isLocal,
                                Boolean isDeleted,
                                Boolean isRemoved,
                                Boolean isNsfw,
                                Boolean restrictedToModerators,
                                String publicKey,
                                String createdAt,
                                String updatedAt

) {

}
