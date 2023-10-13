package com.fedilinks.fedilinksapi.api.lemmy.v3.models.responses;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Language;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.MyUserInfo;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Tagline;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CustomEmojiView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PersonView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.SiteView;
import lombok.Builder;
import lombok.Singular;

import java.util.Collection;

@Builder
public record GetSiteResponse(
        SiteView site_view,
        @Singular Collection<PersonView> admins,
        String version,
        MyUserInfo my_user,
        @Singular Collection<Integer> discussion_languages,
        @Singular Collection<Language> all_languages,
        @Singular Collection<Tagline> taglines,
        @Singular("custom_emoji") Collection<CustomEmojiView> custom_emojis
) {
}