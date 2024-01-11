package com.sublinks.sublinksapi.utils;

import com.sublinks.sublinksapi.utils.models.Mention;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MentionUtilsUnitTests {

  MentionUtils mentionUtils = new MentionUtils();

  @Test
  // Could parameterize this, but I'm not sure if it's worth the hassle
  void givenAVarietyOfMentionStrings_whenMatchedAgainstRegex_thenCorrectlyMatch() {

    // Positive tests
    assertTrue(mentionUtils.personMentionRegex.matcher("@JohnDoe@Sublinks.com").matches());
    assertTrue(mentionUtils.personMentionRegex.matcher("@John_Doe@Sublinks.com").matches());
    assertTrue(mentionUtils.personMentionRegex.matcher("@johndoe@Sublinks.com").matches());
    assertTrue(mentionUtils.personMentionRegex.matcher("@johndoe123@Sublinks2com").matches());
    assertTrue(mentionUtils.personMentionRegex.matcher("@123JohnDoe@Sublinks-com").matches());
    assertTrue(mentionUtils.personMentionRegex.matcher("@123JohnDoe@Sublinks:com").matches());

    // Negative tests
    assertFalse(mentionUtils.personMentionRegex.matcher("@John-Doe@Sublinks.com").matches());
    assertFalse(mentionUtils.personMentionRegex.matcher("@John/Doe@Sublinks.com").matches());
    assertFalse(mentionUtils.personMentionRegex.matcher("@John|Doe@Sublinks.com").matches());
    assertFalse(mentionUtils.personMentionRegex.matcher("@John-DoeAtSublinks.com").matches());
  }

  @Test
  void givenNullString_whenGetPersonMentions_thenReturnNull() {

    assertThrows(NullPointerException.class, () -> mentionUtils.getPersonMentions(null));
  }

  @Test
  void givenEmptyString_whenGetPersonMentions_thenReturnNull() {

    List<Mention> mentions = mentionUtils.getPersonMentions("");

    assertNull(mentions, "List of mentions was not null when passed empty string");
  }

  @Test
  void givenStringWithMultipleMentions_whenGetPersonMentions_thenReturnListWithMultipleMentions() {

    String textWithMentions = "Hello @JohnDoe@Sublinks.com and @Jane_Doe@other-domain.net!";

    List<Mention> mentions = mentionUtils.getPersonMentions(textWithMentions);

    assertEquals(2, mentions.size(), "Number of mentions returned did not match expected");
    assertEquals("JohnDoe", mentions.get(0).name(), "Name of mention did not match expected");
    assertEquals("Sublinks.com", mentions.get(0).domain(), "Domain of mention did not match expected");
    assertEquals("Jane_Doe", mentions.get(1).name(), "Name of mention did not match expected");
    assertEquals("other-domain.net", mentions.get(1).domain(), "Domain of mention did not match expected");
  }

  @Test
  void givenStringWithNoMentions_whenGetPersonMentions_thenReturnNull() {

    String textWithoutMentions = "No mentions to be seen in here!";

    List<Mention> mentions = mentionUtils.getPersonMentions(textWithoutMentions);

    assertNull(mentions, "List of mentions was not null when passed string with no mentions");
  }
}
