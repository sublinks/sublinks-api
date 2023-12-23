package com.sublinks.sublinksapi.utils;

import com.sublinks.sublinksapi.utils.models.Mention;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class MentionUtils {

  public final Pattern personMentionRegex = Pattern.compile(
      "@(?<name>[\\w.]+)@(?<domain>[a-zA-Z0-9._:-]+)", Pattern.CASE_INSENSITIVE);

  /**
   * Extracts person mentions from a given text string.
   * This method utilizes the regex defined in {@code personMentionRegex} to
   * identify mentions in a specific format.
   * A mention is recognized as a string that follows the pattern
   * "@[name]@[domain]",
   * where "name" and "domain" are captured groups in the regex.
   *
   * @param text The string from which person mentions are to be extracted.
   * @return A list of {@link Mention} objects, each representing a found mention
   *         in the text.
   *         Each Mention object contains the extracted "name" and "domain" from
   *         the mention.
   *         Returns null if no mentions are found.
   */
  public List<Mention> getPersonMentions(String text) {

    final Matcher matcher = personMentionRegex.matcher(text);
    List<Mention> mentions = new ArrayList<>();

    while (matcher.find()) {
      mentions.add(Mention.builder()
          .name(matcher.group("name"))
          .domain(matcher.group("domain"))
          .build());
    }
    if (mentions.isEmpty()) {
      return null;
    } else {
      return mentions;
    }
  }
}
