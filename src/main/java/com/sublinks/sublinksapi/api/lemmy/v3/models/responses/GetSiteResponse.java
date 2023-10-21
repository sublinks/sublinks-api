package com.sublinks.sublinksapi.api.lemmy.v3.models.responses;

import com.sublinks.sublinksapi.api.lemmy.v3.models.Language;
import com.sublinks.sublinksapi.api.lemmy.v3.models.MyUserInfo;
import com.sublinks.sublinksapi.api.lemmy.v3.models.Tagline;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CustomEmojiView;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.PersonView;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.SiteView;
import lombok.Builder;

import java.util.Collection;

@Builder
public record GetSiteResponse(
        SiteView site_view,
        Collection<PersonView> admins,
        String version,
        MyUserInfo my_user,
        Collection<Integer> discussion_languages,
        Collection<Language> all_languages,
        Collection<Tagline> taglines,
        Collection<CustomEmojiView> custom_emojis
) {
}