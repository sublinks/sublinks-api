package com.sublinks.sublinksapi.customemoji.dto;

import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomEmojiKeywordTest {

  @Test
  public void testEquals() {
    CustomEmoji e = CustomEmoji.builder().id(12345L).build();
    CustomEmojiKeyword emojiKeyword = CustomEmojiKeyword.builder().keyword("word").emoji(e).id(123L).build();
    CustomEmojiKeyword emojiKeyword2 = CustomEmojiKeyword.builder().keyword("word").emoji(e).id(123L).build();

    assertEquals(emojiKeyword, emojiKeyword2);
  }

  @Test
  public void testNotEquals() {
    CustomEmoji e = CustomEmoji.builder().id(12345L).build();
    CustomEmojiKeyword emojiKeyword = CustomEmojiKeyword.builder().keyword("word").emoji(e).id(123L).build();
    CustomEmojiKeyword emojiKeyword2 = CustomEmojiKeyword.builder().keyword("word").emoji(e).id(1234L).build();

    assertNotEquals(emojiKeyword, emojiKeyword2);
  }

  @Test
  public void testEqualsSameIdDifferentText() {
    CustomEmoji e = CustomEmoji.builder().id(12345L).build();
    CustomEmojiKeyword emojiKeyword = CustomEmojiKeyword.builder().keyword("word").emoji(e).id(123L).build();
    CustomEmojiKeyword emojiKeyword2 = CustomEmojiKeyword.builder().keyword("keyword").emoji(e).id(123L).build();

    assertEquals(emojiKeyword, emojiKeyword2);
  }

  @Test
  public void testEqualsDifferentIdSameText() {
    CustomEmoji e = CustomEmoji.builder().id(12345L).build();
    CustomEmojiKeyword emojiKeyword = CustomEmojiKeyword.builder().keyword("word").emoji(e).id(123L).build();
    CustomEmojiKeyword emojiKeyword2 = CustomEmojiKeyword.builder().keyword("word").emoji(e).id(1234L).build();

    assertNotEquals(emojiKeyword, emojiKeyword2);
  }

  @Test
  public void testNotEqualsToWrongClass() {
    CustomEmoji e = CustomEmoji.builder().id(12345L).build();
    CustomEmojiKeyword emojiKeyword = CustomEmojiKeyword.builder().keyword("word").emoji(e).id(123L).build();

    String abc = "abc";

    assertNotEquals(emojiKeyword, abc);
  }

  @Test
  public void testHashCode() {
    CustomEmoji e = CustomEmoji.builder().id(12345L).build();
    CustomEmojiKeyword emojiKeyword = CustomEmojiKeyword.builder().keyword("word").emoji(e).id(123L).build();
    CustomEmojiKeyword emojiKeyword2 = CustomEmojiKeyword.builder().keyword("word").emoji(e).id(123L).build();

    assertEquals(emojiKeyword.hashCode(), emojiKeyword2.hashCode());
  }

  @Test
  public void testHashCodeEqualsSameIdDifferentText() {
    CustomEmoji e = CustomEmoji.builder().id(12345L).build();
    CustomEmojiKeyword emojiKeyword = CustomEmojiKeyword.builder().keyword("word").emoji(e).id(123L).build();
    CustomEmojiKeyword emojiKeyword2 = CustomEmojiKeyword.builder().keyword("keyword").emoji(e).id(123L).build();

    assertEquals(emojiKeyword.hashCode(), emojiKeyword2.hashCode());
  }

  @Test
  public void testHashCodeEqualsDifferentIdSameText() {
    CustomEmoji e = CustomEmoji.builder().id(12345L).build();
    CustomEmojiKeyword emojiKeyword = CustomEmojiKeyword.builder().keyword("word").emoji(e).id(123L).build();
    CustomEmojiKeyword emojiKeyword2 = CustomEmojiKeyword.builder().keyword("word").emoji(e).id(1234L).build();

    assertEquals(emojiKeyword.hashCode(), emojiKeyword2.hashCode());
  }

  @Test
  public void testHashCodeNotEqualsToWrongClass() {
    CustomEmoji e = CustomEmoji.builder().id(12345L).build();
    CustomEmojiKeyword emojiKeyword = CustomEmojiKeyword.builder().keyword("word").emoji(e).id(123L).build();

    String abc = "abc";

    assertNotEquals(emojiKeyword.hashCode(), abc.hashCode());
  }

  @Test
  public void testEqualsToSelf() {
    CustomEmoji e = CustomEmoji.builder().id(12345L).build();
    CustomEmojiKeyword emojiKeyword = CustomEmojiKeyword.builder().keyword("word").emoji(e).id(123L).build();

    assertTrue(emojiKeyword.equals(emojiKeyword));
  }

  @Test
  public void testEqualsToNull() {
    CustomEmoji e = CustomEmoji.builder().id(12345L).build();
    CustomEmojiKeyword emojiKeyword = CustomEmojiKeyword.builder().keyword("word").emoji(e).id(123L).build();

    assertNotEquals(emojiKeyword, null);
  }

}
