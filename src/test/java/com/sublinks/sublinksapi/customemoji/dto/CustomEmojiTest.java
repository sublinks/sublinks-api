package com.sublinks.sublinksapi.customemoji.dto;

import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomEmojiTest {

  @Test
  public void testEquals() {
    List<CustomEmojiKeyword> keywords = new ArrayList<>();
    CustomEmoji emoji = CustomEmoji.builder()
        .id(12345L)
        .altText("Test Alt Text")
        .createdAt(Date.valueOf("2024-03-04"))
        .shortCode("shortCode")
        .imageUrl("localhost:7777/TestImageLocation")
        .category("Food")
        .keywords(keywords)
        .updatedAt(Date.valueOf("2024-03-05"))
        .build();

    CustomEmoji emoji2 = CustomEmoji.builder()
        .id(12345L)
        .altText("Test Alt Text")
        .createdAt(Date.valueOf("2024-03-04"))
        .shortCode("shortCode")
        .imageUrl("localhost:7777/TestImageLocation")
        .category("Food")
        .keywords(keywords)
        .updatedAt(Date.valueOf("2024-03-05"))
        .build();

      assertEquals(emoji, emoji2);
  }

  @Test
  public void testNotEquals() {
    List<CustomEmojiKeyword> keywords = new ArrayList<>();
    CustomEmoji emoji = CustomEmoji.builder()
        .id(12345L)
        .altText("Test Alt Text")
        .createdAt(Date.valueOf("2024-03-04"))
        .shortCode("shortCode")
        .imageUrl("localhost:7777/TestImageLocation")
        .category("Food")
        .keywords(keywords)
        .updatedAt(Date.valueOf("2024-03-05"))
        .build();

    CustomEmoji emoji2 = CustomEmoji.builder()
        .id(12346L)
        .altText("Test Alt Text")
        .createdAt(Date.valueOf("2024-03-04"))
        .shortCode("shortCode")
        .imageUrl("localhost:7777/TestImageLocation")
        .category("Food")
        .keywords(keywords)
        .updatedAt(Date.valueOf("2024-03-05"))
        .build();

      assertNotEquals(emoji, emoji2);
  }

  @Test
  public void testEqualsSameIdDifferentText() {
    List<CustomEmojiKeyword> keywords = new ArrayList<>();
    CustomEmoji emoji = CustomEmoji.builder()
        .id(12345L)
        .altText("Test Alt Text")
        .createdAt(Date.valueOf("2024-03-04"))
        .shortCode("shortCode")
        .imageUrl("localhost:7777/TestImageLocation")
        .category("Food")
        .keywords(keywords)
        .updatedAt(Date.valueOf("2024-03-05"))
        .build();

    CustomEmoji emoji2 = CustomEmoji.builder()
        .id(12345L)
        .altText("Test Alt Text Number 2")
        .createdAt(Date.valueOf("2024-03-04"))
        .shortCode("shortCode")
        .imageUrl("localhost:7777/TestImageLocation")
        .category("Food")
        .keywords(keywords)
        .updatedAt(Date.valueOf("2024-03-05"))
        .build();

      assertEquals(emoji, emoji2);
  }

  @Test
  public void testEqualsDifferentIdSameText() {
    List<CustomEmojiKeyword> keywords = new ArrayList<>();
    CustomEmoji emoji = CustomEmoji.builder()
        .id(12345L)
        .altText("Test Alt Text")
        .createdAt(Date.valueOf("2024-03-04"))
        .shortCode("shortCode")
        .imageUrl("localhost:7777/TestImageLocation")
        .category("Food")
        .keywords(keywords)
        .updatedAt(Date.valueOf("2024-03-05"))
        .build();

    CustomEmoji emoji2 = CustomEmoji.builder()
        .id(123456L)
        .altText("Test Alt Text")
        .createdAt(Date.valueOf("2024-03-04"))
        .shortCode("shortCode")
        .imageUrl("localhost:7777/TestImageLocation")
        .category("Food")
        .keywords(keywords)
        .updatedAt(Date.valueOf("2024-03-05"))
        .build();

      assertNotEquals(emoji, emoji2);
  }

  @Test
  public void testNotEqualsToWrongClass() {
    List<CustomEmojiKeyword> keywords = new ArrayList<>();
    CustomEmoji emoji = CustomEmoji.builder()
        .id(12345L)
        .altText("Test Alt Text")
        .createdAt(Date.valueOf("2024-03-04"))
        .shortCode("shortCode")
        .imageUrl("localhost:7777/TestImageLocation")
        .category("Food")
        .keywords(keywords)
        .updatedAt(Date.valueOf("2024-03-05"))
        .build();

    String abc = "abc";

    assertNotEquals(emoji, abc);
  }

  @Test
  public void testHashCode() {
    List<CustomEmojiKeyword> keywords = new ArrayList<>();
    CustomEmoji emoji = CustomEmoji.builder()
        .id(12345L)
        .altText("Test Alt Text")
        .createdAt(Date.valueOf("2024-03-04"))
        .shortCode("shortCode")
        .imageUrl("localhost:7777/TestImageLocation")
        .category("Food")
        .keywords(keywords)
        .updatedAt(Date.valueOf("2024-03-05"))
        .build();

    CustomEmoji emoji2 = CustomEmoji.builder()
        .id(12345L)
        .altText("Test Alt Text")
        .createdAt(Date.valueOf("2024-03-04"))
        .shortCode("shortCode")
        .imageUrl("localhost:7777/TestImageLocation")
        .category("Food")
        .keywords(keywords)
        .updatedAt(Date.valueOf("2024-03-05"))
        .build();

    assertEquals(emoji.hashCode(), emoji2.hashCode());
  }

  @Test
  public void testHashCodeEqualsSameIdDifferentText() {
    List<CustomEmojiKeyword> keywords = new ArrayList<>();
    CustomEmoji emoji = CustomEmoji.builder()
        .id(12345L)
        .altText("Test Alt Text")
        .createdAt(Date.valueOf("2024-03-04"))
        .shortCode("shortCode")
        .imageUrl("localhost:7777/TestImageLocation")
        .category("Food")
        .keywords(keywords)
        .updatedAt(Date.valueOf("2024-03-05"))
        .build();

    CustomEmoji emoji2 = CustomEmoji.builder()
        .id(12345L)
        .altText("Test Alt Text Number 2")
        .createdAt(Date.valueOf("2024-03-04"))
        .shortCode("shortCode")
        .imageUrl("localhost:7777/TestImageLocation")
        .category("Food")
        .keywords(keywords)
        .updatedAt(Date.valueOf("2024-03-05"))
        .build();

    assertEquals(emoji.hashCode(), emoji2.hashCode());
  }

  @Test
  public void testHashCodeEqualsDifferentIdSameText() {
    List<CustomEmojiKeyword> keywords = new ArrayList<>();
    CustomEmoji emoji = CustomEmoji.builder()
        .id(12345L)
        .altText("Test Alt Text")
        .createdAt(Date.valueOf("2024-03-04"))
        .shortCode("shortCode")
        .imageUrl("localhost:7777/TestImageLocation")
        .category("Food")
        .keywords(keywords)
        .updatedAt(Date.valueOf("2024-03-05"))
        .build();

    CustomEmoji emoji2 = CustomEmoji.builder()
        .id(123456L)
        .altText("Test Alt Text")
        .createdAt(Date.valueOf("2024-03-04"))
        .shortCode("shortCode")
        .imageUrl("localhost:7777/TestImageLocation")
        .category("Food")
        .keywords(keywords)
        .updatedAt(Date.valueOf("2024-03-05"))
        .build();

    assertEquals(emoji.hashCode(), emoji2.hashCode());
  }

  @Test
  public void testHashCodeNotEqualsToWrongClass() {
    List<CustomEmojiKeyword> keywords = new ArrayList<>();
    CustomEmoji emoji = CustomEmoji.builder()
        .id(12345L)
        .altText("Test Alt Text")
        .createdAt(Date.valueOf("2024-03-04"))
        .shortCode("shortCode")
        .imageUrl("localhost:7777/TestImageLocation")
        .category("Food")
        .keywords(keywords)
        .updatedAt(Date.valueOf("2024-03-05"))
        .build();

    String abc = "abc";

    assertNotEquals(emoji.hashCode(), abc.hashCode());
  }

  @Test
  public void testEqualsToSelf() {
    List<CustomEmojiKeyword> keywords = new ArrayList<>();
    CustomEmoji emoji = CustomEmoji.builder()
        .id(12345L)
        .altText("Test Alt Text")
        .createdAt(Date.valueOf("2024-03-04"))
        .shortCode("shortCode")
        .imageUrl("localhost:7777/TestImageLocation")
        .category("Food")
        .keywords(keywords)
        .updatedAt(Date.valueOf("2024-03-05"))
        .build();

    assertEquals(emoji, emoji);
  }

  @Test
  public void testEqualsToNull() {
    List<CustomEmojiKeyword> keywords = new ArrayList<>();
    CustomEmoji emoji = CustomEmoji.builder()
        .id(12345L)
        .altText("Test Alt Text")
        .createdAt(Date.valueOf("2024-03-04"))
        .shortCode("shortCode")
        .imageUrl("localhost:7777/TestImageLocation")
        .category("Food")
        .keywords(keywords)
        .updatedAt(Date.valueOf("2024-03-05"))
        .build();

    assertNotEquals(emoji, null);
  }

}
