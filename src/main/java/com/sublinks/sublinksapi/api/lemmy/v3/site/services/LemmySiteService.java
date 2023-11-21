package com.sublinks.sublinksapi.api.lemmy.v3.site.services;

import com.sublinks.sublinksapi.api.lemmy.v3.customemoji.models.CustomEmojiView;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.Language;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.LocalSite;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.LocalSiteRateLimit;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.Site;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.SiteAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.SiteView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.models.PersonView;
import com.sublinks.sublinksapi.api.lemmy.v3.user.services.LemmyPersonService;
import com.sublinks.sublinksapi.instance.models.LocalInstanceContext;
import com.sublinks.sublinksapi.language.repositories.LanguageRepository;
import com.sublinks.sublinksapi.person.dto.LinkPersonInstance;
import com.sublinks.sublinksapi.person.enums.LinkPersonInstanceType;
import com.sublinks.sublinksapi.person.repositories.LinkPersonInstanceRepository;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LemmySiteService {

  private final ConversionService conversionService;
  private final LocalInstanceContext localInstanceContext;
  private final LemmyPersonService lemmyPersonService;
  private final LinkPersonInstanceRepository linkPersonInstanceRepository;

  // @todo finish admin list
  public Collection<PersonView> admins() {

    Collection<LinkPersonInstance> admins
        = linkPersonInstanceRepository.getLinkPersonInstancesByInstanceAndLinkTypeIsIn(
        localInstanceContext.instance(),
        List.of(LinkPersonInstanceType.admin, LinkPersonInstanceType.super_admin)
    );
    final Collection<PersonView> adminViews = new LinkedHashSet<>();
    for (LinkPersonInstance linkPersonInstance : admins) {
      adminViews.add(lemmyPersonService.getPersonView(linkPersonInstance.getPerson()));
    }
    return adminViews;
  }

  public Collection<Language> allLanguages(final LanguageRepository languageRepository) {

    final Collection<Language> languages = new LinkedHashSet<>();
    for (com.sublinks.sublinksapi.language.dto.Language language : languageRepository.findAll()) {
      Language l = Language.builder()
          .id(language.getId())
          .code(language.getCode())
          .name(language.getName())
          .build();
      languages.add(l);
    }
    return languages;
  }

  // @todo custom emojis
  public Collection<CustomEmojiView> customEmojis() {

    final Collection<CustomEmojiView> emojiViews = new LinkedHashSet<>();
    return emojiViews;
  }

  public SiteView getSiteView() {

    return SiteView.builder()
        .site(conversionService.convert(localInstanceContext, Site.class))
        .local_site(conversionService.convert(localInstanceContext, LocalSite.class))
        .counts(conversionService.convert(localInstanceContext, SiteAggregates.class))
        .local_site_rate_limit(
            conversionService.convert(localInstanceContext, LocalSiteRateLimit.class))
        .build();
  }
}
