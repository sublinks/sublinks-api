package com.sublinks.sublinksapi.api.lemmy.v3.services;

import com.sublinks.sublinksapi.api.lemmy.v3.models.Language;
import com.sublinks.sublinksapi.api.lemmy.v3.models.views.CustomEmojiView;
import com.sublinks.sublinksapi.language.LanguageRepository;
import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.person.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class SiteService {

    private final PersonRepository personRepository;

    public SiteService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

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
