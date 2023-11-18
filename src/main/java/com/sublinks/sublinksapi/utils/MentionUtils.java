package com.sublinks.sublinksapi.utils;

import com.sublinks.sublinksapi.utils.models.Mention;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class MentionUtils {

  public final Pattern PERSON_MENTION_REGEX = Pattern.compile(
      "@(?<name>[\\w.]+)@(?<domain>[a-zA-Z0-9._:-]+)", Pattern.CASE_INSENSITIVE);

  public List<Mention> getPersonMentions(String text) {

    final Matcher matcher = PERSON_MENTION_REGEX.matcher(text);
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
