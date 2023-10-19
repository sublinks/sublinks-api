package com.fedilinks.fedilinksapi.language;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    Language findLanguageByCode(String code);
}
