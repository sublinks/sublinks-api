package com.sublinks.sublinksapi.api.lemmy.v3.site.services;

import com.sublinks.sublinksapi.api.lemmy.v3.customEmoji.models.CustomEmojiView;
import com.sublinks.sublinksapi.api.lemmy.v3.site.models.Language;
import com.sublinks.sublinksapi.language.LanguageRepository;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.person.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;

@Service
public class SiteService {
    private final PersonRepository personRepository;

    public SiteService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // @todo finish admin list
    public Collection<Person> admins() {

        return personRepository.findAll();
    }

    public Collection<Language> allLanguages(final LanguageRepository languageRepository) {

        final Collection<Language> languages = new LinkedHashSet<>();
        for (com.sublinks.sublinksapi.language.Language language : languageRepository.findAll()) {
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

        final Collection<CustomEmojiView> emojiViews = new HashSet<>();
        return emojiViews;
    }
}
