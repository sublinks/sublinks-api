package com.sublinks.sublinksapi.customemoji.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sublinks.sublinksapi.customemoji.entities.CustomEmoji;
import com.sublinks.sublinksapi.customemoji.entities.CustomEmojiKeyword;
import com.sublinks.sublinksapi.customemoji.repositories.CustomEmojiKeywordRepository;
import com.sublinks.sublinksapi.customemoji.repositories.CustomEmojiRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomEmojiServiceTest {

  @Mock
  CustomEmojiRepository emojiRepository;

  @Mock
  CustomEmojiKeywordRepository emojiKeywordRepository;

  @InjectMocks
  CustomEmojiService customEmojiService;

  CustomEmoji emoji;

  CustomEmojiKeyword emojiKeyword;

  @BeforeEach
  public void setup() {

    emoji = CustomEmoji.builder().id(12345L).build();
    when(emojiRepository.save(any())).thenReturn(emoji);
    emojiKeyword = CustomEmojiKeyword.builder().id(123L).emoji(emoji).build();
  }

  @Test
  public void createCustomEmojiTest() {

    List<String> keywords = new ArrayList<>();
    keywords.add("key");
    keywords.add("word");
    CustomEmoji customEmoji = customEmojiService.createCustomEmoji(emoji, keywords);

    verify(emojiRepository, times(1)).save(any());
    assertNotNull(customEmoji.getKeywords());
  }

  @Test
  public void updateCustomEmojiTest() {

    List<String> keywords = new ArrayList<>();
    keywords.add("key");
    keywords.add("word");
    CustomEmoji customEmoji = customEmojiService.createCustomEmoji(emoji, keywords);

    assertEquals(2, customEmoji.getKeywords().size());

    List<String> words = new ArrayList<>();
    words.add("word");
    words.add("test");
    words.add("this");

    customEmojiService.updateCustomEmoji(customEmoji, words);

    verify(emojiKeywordRepository, times(1)).deleteAll(any());
    verify(emojiRepository, times(2)).save(any());
    assertEquals(3, customEmoji.getKeywords().size());
  }

  @Test
  public void updateCustomEmojiNullListTest() {

    List<String> keywords = new ArrayList<>();
    keywords.add("key");
    keywords.add("word");
    CustomEmoji customEmoji = customEmojiService.createCustomEmoji(emoji, keywords);

    assertEquals(2, customEmoji.getKeywords().size());

    List<String> words = null;

    customEmojiService.updateCustomEmoji(customEmoji, words);

    verify(emojiKeywordRepository, times(1)).deleteAll(any());
    verify(emojiRepository, times(2)).save(any());
    assertEquals(0, customEmoji.getKeywords().size());
  }
}
