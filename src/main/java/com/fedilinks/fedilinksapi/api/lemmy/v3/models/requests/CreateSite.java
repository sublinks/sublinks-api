package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

import java.util.Collection;

@Builder
public record CreateSite(
        String auth,
        String name,
        String sidebar,
        String description,
        String icon,
        String banner,
        Boolean enableDownvotes,
        Boolean enableNsfw,
        Boolean communityCreationAdminOnly,
        Boolean requireEmailVerification,
        String applicationQuestion,
        Boolean privateInstance,
        String defaultTheme,
        String defaultPostListingType,
        String legalInformation,
        Boolean applicationEmailAdmins,
        Boolean hideModlogModNames,
        Collection<String> discussionLanguages,
        String slurFilterRegex,
        Integer actorNameMaxLength,
        Integer rateLimitMessage,
        Integer rateLimitMessagePerSecond,
        Integer rateLimitPost,
        Integer rateLimitPostPerSecond,
        Integer rateLimitRegister,
        Integer rateLimitRegisterPerSecond,
        Integer rateLimitImage,
        Integer rateLimitImagePerSecond,
        Integer rateLimitComment,
        Integer rateLimitCommentPerSecond,
        Integer rateLimitSearch,
        Integer rateLimitSearchPerSecond,
        Boolean federationEnabled,
        Boolean federationDebug,
        Boolean captchaEnabled,
        String captchaDifficulty,
        Collection<String> allowedInstances,
        Collection<String> blockedInstances,
        Collection<String> taglines,
        String registrationMode
) {
}