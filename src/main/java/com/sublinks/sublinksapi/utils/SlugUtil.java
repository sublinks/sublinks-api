package com.sublinks.sublinksapi.utils;

import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class SlugUtil {

  /**
   * Converts a given string to a slug format.
   * This method transforms the input string to lowercase, replaces new lines and
   * non-alphanumeric characters
   * with spaces, and then collapses multiple spaces into a single underscore.
   *
   * @param title The string to be converted into slug format.
   * @return A slug version of the provided string.
   */
  public String stringToSlug(String title) {

    return title
        .toLowerCase()
        .replace("\n", " ")
        .replace("[^a-z\\d\\s]", " ")
        .replaceAll("\\s+", "_");
  }

  /**
   * Generates a unique slug for a given title.
   * This method combines a randomly generated string with a slug version of the
   * provided title,
   * separated by a hyphen. The random string ensures uniqueness of the slug.
   *
   * @param title The title for which a unique slug is to be generated.
   * @return A unique slug for the given title.
   */
  public String uniqueSlug(String title) {

    return randomStringGenerator() + "-" + stringToSlug(title);
  }

  /**
   * Generates a random string of lowercase alphabetic characters.
   * This method creates a random string of 8 characters in the range 'a' to 'z'.
   *
   * @return A random string of 8 lowercase letters.
   */
  public String randomStringGenerator() {

    final Random random = new Random();
    return random.ints(97, 123)
        .limit(8)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }
}
