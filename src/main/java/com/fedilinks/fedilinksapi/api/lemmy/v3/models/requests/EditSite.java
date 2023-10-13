package com.fedilinks.fedilinksapi.api.lemmy.v3.models.requests;

import lombok.Builder;

import java.util.Collection;

@Builder
public record EditSite(
        String auth,
        String name,
        String sidebar,
        String description,
        String icon,
        String banner,
        boolean enableDownvotes,
        boolean enableNsfw,
        boolean communityCreationAdminOnly,
        boolean requireEmailVerification,
        String applicationQuestion,
        boolean privateInstance,
        String defaultTheme,
        String defaultPostListingType,
        String legalInformation,
        boolean applicationEmailAdmins,
        boolean hideModlogModNames,
        Collection<String> discussionLanguages,
        String slurFilterRegex,
        int actorNameMaxLength,
        int rateLimitMessage,
        int rateLimitMessagePerSecond,
        int rateLimitPost,
        int rateLimitPostPerSecond,
        int rateLimitRegister,
        int rateLimitRegisterPerSecond,
        int rateLimitImage,
        int rateLimitImagePerSecond,
        int rateLimitComment,
        int rateLimitCommentPerSecond,
        int rateLimitSearch,
        int rateLimitSearchPerSecond,
        boolean federationEnabled,
        boolean federationDebug,
        boolean captchaEnabled,
        String captchaDifficulty,
        Collection<String> allowedInstances,
        Collection<String> blockedInstances,
        Collection<String> taglines,
        String registrationMode
) {
}