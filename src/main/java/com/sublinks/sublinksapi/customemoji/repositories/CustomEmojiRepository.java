package com.sublinks.sublinksapi.customemoji.repositories;

import com.sublinks.sublinksapi.customemoji.dto.CustomEmoji;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomEmojiRepository extends JpaRepository<CustomEmoji, Long> {

}
