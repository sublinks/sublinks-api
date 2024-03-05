package com.sublinks.sublinksapi.utils;

import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.Base64;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.substringBetween;

@AllArgsConstructor
public class CursorBasedPageable {

  private final String prefix;
  private final String nextPageCursor;

  public boolean hasNextPageCursor() {

    return nextPageCursor != null && !nextPageCursor.isEmpty();
  }

  public String getNextPageDecodedCursor() {

    if (nextPageCursor == null || nextPageCursor.isEmpty()) {
      throw new IllegalArgumentException("Cursor value is not valid!");
    }
    var decodedBytes = Base64.getDecoder().decode(nextPageCursor);
    var decodedValue = new String(decodedBytes);

    return substringBetween(decodedValue, "###");
  }

  public String encodeField(String field) {

    requireNonNull(field);

    var structuredValue = "###" + field + "###-" + LocalDateTime.now();
    return prefix + Base64.getEncoder().encodeToString(structuredValue.getBytes());
  }

  public String getNextEncodedCursor() {

    return encodeField(nextPageCursor);
  }
}