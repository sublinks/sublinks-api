package com.sublinks.sublinksapi.slurfilter.services;

import com.sublinks.sublinksapi.slurfilter.dto.SlurFilter;
import com.sublinks.sublinksapi.slurfilter.enums.SlurActionType;
import com.sublinks.sublinksapi.slurfilter.exceptions.SlurFilterBlockedException;
import com.sublinks.sublinksapi.slurfilter.exceptions.SlurFilterReportException;
import com.sublinks.sublinksapi.slurfilter.repositories.SlurFilterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SlurFilterServiceUnitTests {

  @Mock
  SlurFilterRepository slurFilterRepository;
  @Captor
  ArgumentCaptor<SlurFilter> slurFilterArgumentCaptor;
  @InjectMocks
  SlurFilterService slurFilterService;

  @Test
  void givenFilterDoesNotExist_whenUpdateOrCreateLemmySlur_thenCreateAndSaveFilter() {

    String regex = "exampleRegex";

    when(slurFilterRepository.findAll()).thenReturn(List.of());
    slurFilterService.updateOrCreateLemmySlur(regex);

    verify(slurFilterRepository, times(1)).findAll();
    verify(slurFilterRepository, times(1)).save(any(SlurFilter.class));
    verify(slurFilterRepository).save(slurFilterArgumentCaptor.capture());
    assertEquals(regex, slurFilterArgumentCaptor.getValue().getSlurRegex(),
        "Saved slur regex did not match");
    assertEquals(SlurActionType.BLOCK, slurFilterArgumentCaptor.getValue().getSlurActionType(),
        "Saved slur action type was not BLOCK");
  }

  @Test
  void givenExistingFilter_whenUpdateOrCreateLemmySlur_thenReplaceFilter() {

    String regex = "exampleRegex";
    SlurFilter existingFilter = new SlurFilter();
    existingFilter.setSlurRegex("oldRegex");

    when(slurFilterRepository.findAll()).thenReturn(List.of(existingFilter));

    slurFilterService.updateOrCreateLemmySlur(regex);

    verify(slurFilterRepository, times(1)).findAll();
    verify(slurFilterRepository, times(1)).save(existingFilter);
    assertEquals(regex, existingFilter.getSlurRegex(), "Saved slur regex was not updated");
  }

  @Test
  void givenFilterDoesNotExist_whenGetLemmySlurFilter_thenCreateSaveAndReturnFilter() {

    when(slurFilterRepository.findAll()).thenReturn(Collections.emptyList());

    SlurFilter slurFilter = slurFilterService.getLemmySlurFilter();

    verify(slurFilterRepository, times(1)).findAll();
    verify(slurFilterRepository, times(1)).save(any(SlurFilter.class));
    verify(slurFilterRepository).save(slurFilterArgumentCaptor.capture());
    assertEquals("", slurFilterArgumentCaptor.getValue().getSlurRegex(),
        "Created slur regex was not an empty string");
    assertEquals(SlurActionType.BLOCK, slurFilterArgumentCaptor.getValue().getSlurActionType(),
        "Created slur action type was not BLOCK");
    assertEquals("", slurFilter.getSlurRegex(), "Returned slur regex was not an empty string");
    assertEquals(SlurActionType.BLOCK, slurFilter.getSlurActionType(),
        "Returned slur action type was not BLOCK");
  }

  @Test
  void givenExistingFilter_whenGetLemmySlurFilter_thenReturnExistingFilter() {

    SlurFilter existingFilter = new SlurFilter();
    existingFilter.setSlurRegex("oldRegex");

    when(slurFilterRepository.findAll()).thenReturn(List.of(existingFilter));

    SlurFilter slurFilter = slurFilterService.getLemmySlurFilter();

    verify(slurFilterRepository, times(1)).findAll();
    verify(slurFilterRepository, times(1)).save(existingFilter);
    verify(slurFilterRepository).save(slurFilterArgumentCaptor.capture());
    assertEquals("oldRegex", slurFilter.getSlurRegex(),
        "Returned slur regex was not existing value");
  }

  @Test
  void givenNullText_whenGetHighestSlurFilterMatchingText_thenReturnNull() {

    when(slurFilterRepository.findAll()).thenReturn(Collections.emptyList());

    SlurFilter slurFilter = slurFilterService.getHighestSlurFilterMatchingText(null);

    verify(slurFilterRepository, times(1)).findAll();
    assertNull(slurFilter);
  }

  @Test
  void givenEmptyText_whenGetHighestSlurFilterMatchingText_thenReturnNull() {

    when(slurFilterRepository.findAll()).thenReturn(Collections.emptyList());

    SlurFilter slurFilter = slurFilterService.getHighestSlurFilterMatchingText("");

    verify(slurFilterRepository, times(1)).findAll();
    assertNull(slurFilter);
  }

  @Test
  void givenBlankText_whenGetHighestSlurFilterMatchingText_thenReturnNull() {

    when(slurFilterRepository.findAll()).thenReturn(Collections.emptyList());

    SlurFilter slurFilter = slurFilterService.getHighestSlurFilterMatchingText("    ");

    verify(slurFilterRepository, times(1)).findAll();
    assertNull(slurFilter);
  }

  @Test
  void givenNoFilters_whenGetHighestSlurFilterMatchingText_thenReturnNull() {

    when(slurFilterRepository.findAll()).thenReturn(Collections.emptyList());

    SlurFilter slurFilter = slurFilterService.getHighestSlurFilterMatchingText("some text");

    verify(slurFilterRepository, times(1)).findAll();
    assertNull(slurFilter);
  }

  @Test
  void givenTextMatchingNoFilters_whenGetHighestSlurFilterMatchingText_thenReturnMatchingFilter() {

    SlurFilter existingFilter = new SlurFilter();
    existingFilter.setSlurRegex("\\bword.+?\\b");

    when(slurFilterRepository.findAll()).thenReturn(List.of(existingFilter));

    SlurFilter slurFilter = slurFilterService.getHighestSlurFilterMatchingText("Something");

    verify(slurFilterRepository, times(1)).findAll();
    assertNull(slurFilter, "Returned filter was not null");
  }

  @Test
  void givenMatchingText_whenGetHighestSlurFilterMatchingText_thenReturnMatchingFilter() {

    SlurFilter existingFilter = new SlurFilter();
    existingFilter.setSlurRegex("\\bword.+?\\b");

    when(slurFilterRepository.findAll()).thenReturn(List.of(existingFilter));

    SlurFilter slurFilter = slurFilterService.getHighestSlurFilterMatchingText("Some words");

    verify(slurFilterRepository, times(1)).findAll();
    assertEquals(existingFilter, slurFilter, "Expected filter was not returned");
  }

  @Test
  void givenTextMatchingMultipleFilters_whenGetHighestSlurFilterMatchingText_thenReturnMostSevereFilter() {

    SlurFilter reportFilter = new SlurFilter();
    reportFilter.setSlurRegex("\\bword.+?\\b");
    reportFilter.setSlurActionType(SlurActionType.REPORT);
    SlurFilter replaceFilter = new SlurFilter();
    replaceFilter.setSlurRegex("\\bword.+?\\b");
    replaceFilter.setSlurActionType(SlurActionType.REPLACE);
    SlurFilter blockFilter = new SlurFilter();
    blockFilter.setSlurRegex("\\bword.+?\\b");
    blockFilter.setSlurActionType(SlurActionType.BLOCK);

    when(slurFilterRepository.findAll()).thenReturn(List.of(reportFilter, replaceFilter, blockFilter));

    SlurFilter slurFilter = slurFilterService.getHighestSlurFilterMatchingText("Some words");

    verify(slurFilterRepository, times(1)).findAll();
    assertEquals(blockFilter, slurFilter, "Expected filter was not returned");
  }

  @Test
  void givenTextThatDoesNotNeedCensoring_whenCensorText_thenReturnText()
       throws SlurFilterReportException, SlurFilterBlockedException {

    String safeText = "Nothing wrong with this";
    SlurFilter existingFilter = new SlurFilter();
    existingFilter.setSlurRegex("existingRegex");

    when(slurFilterRepository.findAll()).thenReturn(List.of(existingFilter));

    String resultText = slurFilterService.censorText(safeText);

    verify(slurFilterRepository, times(1)).findAll();
    assertEquals(safeText, resultText, "Text was censored when it should not have been");
  }

  @Test
  void givenTextMatchingBlockFilter_whenCensorText_thenThrowSlurFilterBlockedException() {

    String unsafeText = "Some mean words";
    SlurFilter blockFilter = new SlurFilter();
    blockFilter.setSlurRegex("\\bword.+?\\b");
    blockFilter.setSlurActionType(SlurActionType.BLOCK);

    when(slurFilterRepository.findAll()).thenReturn(List.of(blockFilter));

    SlurFilterBlockedException exception = assertThrows(SlurFilterBlockedException.class, () ->
        slurFilterService.censorText(unsafeText));

    verify(slurFilterRepository, times(1)).findAll();
    assertEquals("Text blocked by slur filter", exception.getMessage(), "Unexpected message");
    assertEquals(blockFilter, exception.getSlurFilter(), "Unexpected filter");
  }

  @Test
  void givenTextMatchingReportFilter_whenCensorText_thenThrowSlurFilterReportException() {

    String unsafeText = "Some mean words";
    SlurFilter reportFilter = new SlurFilter();
    reportFilter.setSlurRegex("\\bword.+?\\b");
    reportFilter.setSlurActionType(SlurActionType.REPORT);

    when(slurFilterRepository.findAll()).thenReturn(List.of(reportFilter));

    SlurFilterReportException exception = assertThrows(SlurFilterReportException.class, () ->
        slurFilterService.censorText(unsafeText));

    verify(slurFilterRepository, times(1)).findAll();
    assertEquals("Text should be reported", exception.getMessage(), "Unexpected message");
    assertEquals(reportFilter, exception.getSlurFilter(), "Unexpected filter");
  }

  @Test
  void givenNullText_whenCensorText_thenReturnNullText()
      throws SlurFilterReportException, SlurFilterBlockedException {

    SlurFilter replaceFilter = new SlurFilter();
    replaceFilter.setSlurRegex("\\bword.+?\\b");
    replaceFilter.setSlurActionType(SlurActionType.REPLACE);

    when(slurFilterRepository.findAll()).thenReturn(List.of(replaceFilter));

    String resultText = slurFilterService.censorText(null);

    verify(slurFilterRepository, times(1)).findAll();
    assertNull(resultText, "Null was not returned when passed as a parameter");
  }

  @Test
  void givenEmptyText_whenCensorText_thenReturnEmptyText()
      throws SlurFilterReportException, SlurFilterBlockedException {

    SlurFilter replaceFilter = new SlurFilter();
    replaceFilter.setSlurRegex("\\bword.+?\\b");
    replaceFilter.setSlurActionType(SlurActionType.REPLACE);

    when(slurFilterRepository.findAll()).thenReturn(List.of(replaceFilter));

    String resultText = slurFilterService.censorText("");

    verify(slurFilterRepository, times(1)).findAll();
    assertEquals("", resultText, "Returned text was not empty");
  }

  @Test
  void givenBlankText_whenCensorText_thenReturnBlankText()
      throws SlurFilterReportException, SlurFilterBlockedException {

    SlurFilter replaceFilter = new SlurFilter();
    replaceFilter.setSlurRegex("\\bword.+?\\b");
    replaceFilter.setSlurActionType(SlurActionType.REPLACE);

    when(slurFilterRepository.findAll()).thenReturn(List.of(replaceFilter));

    String resultText = slurFilterService.censorText("        ");

    verify(slurFilterRepository, times(1)).findAll();
    assertEquals("        ", resultText, "Returned text was not blank");
  }

  @Test
  void givenNoFiltersForText_whenCensorText_thenReturnText()
      throws SlurFilterReportException, SlurFilterBlockedException {

    when(slurFilterRepository.findAll()).thenReturn(Collections.emptyList());

    String resultText = slurFilterService.censorText("text");

    verify(slurFilterRepository, times(1)).findAll();
    assertEquals("text", resultText, "Returned text was not blank");
  }

  @Test
  void givenTextNeedingCensoring_whenCensorText_thenCensoredText()
      throws SlurFilterReportException, SlurFilterBlockedException {

    SlurFilter replaceFilter = new SlurFilter();
    replaceFilter.setSlurRegex("mean");
    replaceFilter.setSlurActionType(SlurActionType.REPLACE);

    SlurFilter anotherReplaceFilter = new SlurFilter();
    anotherReplaceFilter.setSlurRegex("\\bword.+?\\b");
    anotherReplaceFilter.setSlurActionType(SlurActionType.REPLACE);

    when(slurFilterRepository.findAll()).thenReturn(List.of(replaceFilter, anotherReplaceFilter));
    when(slurFilterRepository.findBySlurActionType(SlurActionType.REPLACE)).thenReturn(List.of(replaceFilter, anotherReplaceFilter));

    String resultText = slurFilterService.censorText("Some mean words in here.");

    verify(slurFilterRepository, times(1)).findAll();
    verify(slurFilterRepository, times(1)).findBySlurActionType(SlurActionType.REPLACE);

    assertEquals("Some **** *********** in here.", resultText, "Censored text did not match expected");
  }
}
