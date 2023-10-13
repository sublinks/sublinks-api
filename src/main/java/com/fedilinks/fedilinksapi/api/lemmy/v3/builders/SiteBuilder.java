package com.fedilinks.fedilinksapi.api.lemmy.v3.builders;

import com.fedilinks.fedilinksapi.api.lemmy.v3.models.Language;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.CustomEmojiView;
import com.fedilinks.fedilinksapi.api.lemmy.v3.models.views.PersonView;
import com.fedilinks.fedilinksapi.language.LanguageRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

@Component
public class SiteBuilder {

    public Collection<PersonView> admins() {
        final Collection<PersonView> personViews = new HashSet<>();
        return personViews;
    }

    public Collection<Language> allLanguages(final LanguageRepository languageRepository) {
        final Collection<Language> languages = new LinkedHashSet<>();
        for (com.fedilinks.fedilinksapi.language.Language language : languageRepository.findAll()) {
            Language l = Language.builder()
                    .id(language.getId())
                    .code(language.getCode())
                    .name(language.getName())
                    .build();
            languages.add(l);
        }
        return languages;
    }

    public Collection<CustomEmojiView> customEmojis() {
        final Collection<CustomEmojiView> emojiViews = new HashSet<>();
        return emojiViews;
    }

    public Collection<Integer> discussionLanguages() {
        List<Integer> languageIds = new ArrayList<>();
        languageIds.add(1);
        languageIds.add(38);
        return languageIds;
    }
}
