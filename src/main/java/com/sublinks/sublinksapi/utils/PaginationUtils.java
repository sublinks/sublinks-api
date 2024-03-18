package com.sublinks.sublinksapi.utils;

import com.sublinks.sublinksapi.utils.models.CursorPageable;
import jakarta.persistence.TypedQuery;
import org.springframework.lang.Nullable;

import java.util.Date;

import static java.util.Objects.requireNonNull;

public class PaginationUtils {

  /**
   * Calculates the offset for pagination query.
   *
   * @param page The page number.
   * @param size The number of items per page.
   * @return The starting index for the given page.
   */
  public static int getOffset(int page, int size) {

    return (page - 1) * size;
  }

  /**
   * Applies pagination to a {@link TypedQuery}.
   *
   * @param <T>   The type of the query result.
   * @param query The TypedQuery to which pagination will be applied.
   * @param page  The page number to retrieve.
   * @param size  The number of records per page.
   */
  public static <T> void applyPagination(TypedQuery<T> query, @Nullable Integer page,
      Integer size) {

    if (page != null) {
      query.setFirstResult(getOffset(page, size));
    }
    query.setMaxResults(Math.abs(size));
  }
}
