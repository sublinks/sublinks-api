package com.sublinks.sublinksapi.api.lemmy.v3.site.models;

import lombok.Builder;

import java.util.Collection;

@Builder
public record EditSite(
        
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