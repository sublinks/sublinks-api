package com.sublinks.sublinksapi.language;

import com.sublinks.sublinksapi.instance.Instance;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageService(final LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<Long> instanceLanguageIds(final Instance instance) {

        final List<Long> discussionLanguages = new ArrayList<>();
        for (Language language : instance.getLanguages()) {
            discussionLanguages.add(language.getId());
        }
        return discussionLanguages;
    }

    public List<Language> languageIdsToEntity(final Collection<String> languageIds) {

        final List<Language> languages = new ArrayList<>();
        for (String languageCode : languageIds) {
            final Optional<Language> language = languageRepository.findById(Long.valueOf(languageCode));
            language.ifPresent(languages::add);
        }
        return languages;
    }
}
