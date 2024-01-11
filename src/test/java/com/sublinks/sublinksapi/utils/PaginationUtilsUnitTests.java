package com.sublinks.sublinksapi.utils;

import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PaginationUtilsUnitTests {

  @Mock
  TypedQuery<Object> typedQuery;

  @Test
  void givenPageOf2SizeOf10_whenGetOffset_thenReturnCorrectOffset() {

    int page = 3;
    int size = 10;

    int offset = PaginationUtils.getOffset(page, size);

    assertEquals(20, offset);
  }

  @Test
  void givenTypedQueryPageOf3SizeOf10_whenApplyPagination_thenCorrectTypedQueryCalls() {

    int page = 3;
    int size = 10;

    PaginationUtils.applyPagination(typedQuery, page, size);

    Mockito.verify(typedQuery).setFirstResult(20);
    Mockito.verify(typedQuery).setMaxResults(10);
  }
}
