package com.sublinks.sublinksapi.utils;

import com.sublinks.sublinksapi.utils.models.CursorPageable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.substringBetween;

@Getter
public class CursorBasedPageable<O extends CursorPageable> {

  private String prefix;
  private String next;
  private String primary;
  private String secondary;

  public CursorBasedPageable(String prefix, String next) {

    requireNonNull(prefix);
    requireNonNull(next);

    this.prefix = prefix;
    this.next = next.substring(this.prefix.length());

    final Map<String, String> values = getNextPageDecodedCursor();
    primary = values.get("primary");
    secondary = values.get("secondary");
  }

  public CursorBasedPageable(String prefix, String primary_data, String secondary_data) {

    this.prefix = prefix;
    this.primary = primary_data;
    this.secondary = secondary_data;
  }

  private Map<String, String> splitDecodedValue(String decodedValue, String delimiter) {

    var values = decodedValue.split(delimiter);
    return Map.of("primary", values[0], "secondary", values[1]);
  }

  public Map<String, String> getNextPageDecodedCursor() {

    if (next == null || next.isEmpty()) {
      throw new IllegalArgumentException("Cursor value is not valid!");
    }
    var decodedBytes = Base64.getDecoder().decode(next);
    var decodedValue = new String(decodedBytes);

    Map<String, String> values = splitDecodedValue(substringBetween(decodedValue, "###"), "-#-");

    primary = values.get("primary");
    secondary = values.get("secondary");

    return values;
  }

  public String encodeField(String primary, String secondary) {

    requireNonNull(primary);
    requireNonNull(secondary);

    var structuredValue = "###" + primary + "-#-" + secondary + "###-" + LocalDateTime.now();
    return prefix + Base64.getEncoder().encodeToString(structuredValue.getBytes());
  }

  public String getNextEncodedCursor() {

    return encodeField(primary, secondary);
  }

  public static <T extends CursorPageable> CursorBasedPageable<T> getCursor(String prefix,
      String next) {

    requireNonNull(next);

    return new CursorBasedPageable<T>(prefix, next);
  }
}