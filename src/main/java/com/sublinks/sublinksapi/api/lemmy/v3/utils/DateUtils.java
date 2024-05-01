package com.sublinks.sublinksapi.api.lemmy.v3.utils;

import java.time.format.DateTimeFormatter;

/**
 * Utility class for date formatting and parsing.
 */
public class DateUtils {

  public static final String FRONT_END_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX";
  public static DateTimeFormatter FRONT_END_DATETIME_FORMATTER = DateTimeFormatter.ofPattern(
      DateUtils.FRONT_END_DATE_FORMAT);
}
