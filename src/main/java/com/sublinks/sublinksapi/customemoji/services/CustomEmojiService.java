package com.sublinks.sublinksapi.customemoji.services;

import java.util.ArrayList;

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
    return this.addKeywords(customEmoji, keywords);
  }

  @Transactional
  public CustomEmoji updateCustomEmoji(final CustomEmoji customEmoji, final Iterable<String> keywords) {
    customEmojiKeywordRepository.deleteAll(customEmoji.getKeywords());
    return this.addKeywords(customEmoji, keywords);
  }

  private CustomEmoji addKeywords(final CustomEmoji customEmoji, final Iterable<String> keywords) {
    var customEmojiKeywords = new ArrayList<CustomEmojiKeyword>();
    if (keywords != null) {
      for (var keyword : keywords) {
        customEmojiKeywords.add(
            CustomEmojiKeyword.builder()
                .keyword(keyword.toLowerCase().trim())
                .emoji(customEmoji)
                .build());
      }
    }
    customEmoji.setKeywords(customEmojiKeywords);
    return customEmojiRepository.save(customEmoji);
  }

}
