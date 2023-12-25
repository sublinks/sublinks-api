package com.sublinks.sublinksapi.customemoji.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sublinks.sublinksapi.customemoji.dto.CustomEmoji;
import com.sublinks.sublinksapi.customemoji.dto.CustomEmojiKeyword;
import com.sublinks.sublinksapi.customemoji.repositories.CustomEmojiKeywordRepository;
import com.sublinks.sublinksapi.customemoji.repositories.CustomEmojiRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomEmojiService {

  private final CustomEmojiRepository customEmojiRepository;
  private final CustomEmojiKeywordRepository customEmojiKeywordRepository;

  @Transactional
  public CustomEmoji createCustomEmoji(final CustomEmoji customEmoji, final Iterable<String> keywords) {

    var emojiEntity = customEmojiRepository.save(customEmoji);

    var customEmojiKeywords = new ArrayList<CustomEmojiKeyword>();
    for (var keyword : keywords) {
      customEmojiKeywords.add(
          CustomEmojiKeyword.builder()
              .keyword(keyword.toLowerCase().trim())
              .emoji(emojiEntity)
              .build());
    }
    customEmojiKeywordRepository.saveAll(customEmojiKeywords);

    return emojiEntity;
  }

}
