package com.sublinks.sublinksapi.utils;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SlugUtilUnitTest {

  SlugUtil slugUtil = new SlugUtil();

  Pattern uniquePatter = Pattern.compile("^[a-z]{8}$");

  @Test
  void givenStringWithNewLines_whenStringToSlug_thenReturnStringAsSlug() {

    String titleString = "\n\n\nthis\nis\na\nstring\n\n\n";

    String titleAsSlug = slugUtil.stringToSlug(titleString);

    assertEquals("_this_is_a_string_", titleAsSlug,
        "New lines in a title were not turned into/collapsed into underscores");
  }

  @Test
  @Disabled // Issue #173
  void givenStringWithNonAlphaNumericCharacters_whenStringToSlug_thenReturnStringAsSlug() {

    String titleString = "[])this(\\is}{*a&-string#%^";

    String titleAsSlug = slugUtil.stringToSlug(titleString);

    assertEquals("_this_is_a_string_", titleAsSlug,
        "Non-alphanumeric characters in a title were not turned into/collapsed into underscores");
  }

  @Test
  void givenStringWithExtraSpaces_whenStringToSlug_thenReturnStringAsSlug() {

    String titleString = "    this     is     a    string     ";

    String titleAsSlug = slugUtil.stringToSlug(titleString);

    assertEquals("_this_is_a_string_", titleAsSlug,
        "Spaces in a title were not turned into/collapsed into underscores");
  }

  @Test
  void givenTitleString_whenUniqueSlug_thenReturnRandomStringAndSlugSeparatedByHyphen() {

    String titleString = "THIS IS A STRING";

    String uniqueSlug = slugUtil.uniqueSlug(titleString);

    assertTrue(uniquePatter.matcher(uniqueSlug.substring(0,8)).matches(),
        "Randomly generated string was not 8 a-z characters");

    assertEquals("-this_is_a_string", uniqueSlug.substring(8),
        "Title was not turned into slug version correctly");
  }

  @Test
  void givenStringWithUppercaseCharacters_whenStringToSlug_thenReturnStringAsSlug() {

    String titleString = "THIS IS A STRING";

    String titleAsSlug = slugUtil.stringToSlug(titleString);

    assertEquals("this_is_a_string", titleAsSlug,
        "Uppercase characters were not turned to lowercase");
  }

  @Test
  void whenRandomStringGenerator_thenReturnRandomString() {

    String randomString = slugUtil.randomStringGenerator();

    assertTrue(uniquePatter.matcher(randomString).matches(),
        "Randomly generated string was not 8 a-z characters");
  }

}
