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
    this.addKeywords(emojiEntity, keywords);
    return emojiEntity;
  }

  @Transactional
  public CustomEmoji updateCustomEmoji(final CustomEmoji customEmoji, final Iterable<String> keywords) {
    var emojiEntity = customEmojiRepository.save(customEmoji);
    customEmojiKeywordRepository.deleteAll(emojiEntity.getKeywords());
    this.addKeywords(emojiEntity, keywords);
    return emojiEntity;
  }

  private void addKeywords(final CustomEmoji customEmoji, final Iterable<String> keywords) {
    var customEmojiKeywords = new ArrayList<CustomEmojiKeyword>();
    for (var keyword : keywords) {
      customEmojiKeywords.add(
          CustomEmojiKeyword.builder()
              .keyword(keyword.toLowerCase().trim())
              .emoji(customEmoji)
              .build());
    }
    customEmojiKeywordRepository.saveAll(customEmojiKeywords);
  }

}
