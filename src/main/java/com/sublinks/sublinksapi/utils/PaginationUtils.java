package com.sublinks.sublinksapi.utils;

import jakarta.persistence.TypedQuery;

public class PaginationUtils {

  public static int getOffset(int page, int size) {

    return (page - 1) * size;
  }


  public static <T> void applyPagination(TypedQuery<T> query, int page, int size) {

    query.setFirstResult(getOffset(page, size));
    query.setMaxResults(Math.abs(size));
  }
}
