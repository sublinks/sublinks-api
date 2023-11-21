package com.sublinks.sublinksapi.api.lemmy.v3.site.models;

import com.sublinks.sublinksapi.api.lemmy.v3.customEmoji.models.CustomEmojiView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.MyUserInfo;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonView;
import java.util.Collection;
import lombok.Builder;

@Builder
public record GetSiteResponse(
    SiteView site_view,
    Collection<PersonView> admins,
    String version,
    MyUserInfo my_user,
    Collection<Long> discussion_languages,
    Collection<Language> all_languages,
    Collection<Tagline> taglines,
    Collection<CustomEmojiView> custom_emojis
) {

}