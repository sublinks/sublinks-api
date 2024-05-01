package com.sublinks.sublinksapi.api.lemmy.v3.utils;

import org.springframework.lang.Nullable;

/**
 * The PaginationControllerUtils class provides utility methods for handling pagination-related
 * tasks.
 */
public class PaginationControllerUtils {

  /**
   * Returns the absolute minimum number between the given number and default number. If both the
   * number and default number are null, returns 1. If the default number is null, assigns the value
   * of number to default number. If the number is null, returns the absolute value of default
   * number.
   *
   * @param number        the number to compare
   * @param defaultNumber the default number to compare
   * @return the absolute minimum number between the given number and default number
   */
  public static int getAbsoluteMinNumber(@Nullable Integer number,
      @Nullable Integer defaultNumber) {

    if (number == null && defaultNumber == null) {
      return 1;
    }

    if (defaultNumber == null) {
      defaultNumber = number;
    }

    if (number == null) {
      return Math.abs(defaultNumber);
    }

    return Math.abs(Math.min(number, defaultNumber));
  }

}
