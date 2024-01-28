package com.sublinks.sublinksapi.utils;

import com.sublinks.sublinksapi.utils.SiteMetadataUtil.SiteMetadata;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class SiteMetadataUtilUnitTests {

  SiteMetadataUtil siteMetadataUtil = new SiteMetadataUtil();

  @Mock
  Connection connection;
  @Mock
  Document document;
  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  Elements titleElement;
  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  Elements descriptionElement;
  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  Elements imageElement;
  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  Elements videoElement;

  @Test
  void givenUrlThatCausesIOException_whenFetchSiteMetadata_thenReturnSiteMetadataWithNullFields() throws IOException {

    String urlThatCausesIOException = "https://www.sublinks.com";

    try (MockedStatic<Jsoup> jsoupMockedStatic = Mockito.mockStatic(Jsoup.class)) {
      jsoupMockedStatic.when(() -> Jsoup.connect(urlThatCausesIOException)).thenReturn(connection);
      Mockito.when(connection.get()).thenThrow(new IOException("Oh no! An exception!"));

      SiteMetadata siteMetadata = siteMetadataUtil.fetchSiteMetadata(urlThatCausesIOException);

      assertNull(siteMetadata.title(),
          "SiteMetadata title should be null when an IOException is thrown");
      assertNull(siteMetadata.description(),
          "SiteMetadata description should be null when an IOException is thrown");
      assertNull(siteMetadata.imageUrl(),
          "SiteMetadata imageUrl should be null when an IOException is thrown");
      assertNull(siteMetadata.videoUrl(),
          "SiteMetadata videoUrl should be null when an IOException is thrown");
    }
  }

  @Test
  void givenUrlWithNoOgTags_whenFetchSiteMetadata_thenReturnSiteMetadataFallingBackOnHtmlTags() throws IOException {

    String normalizedUrl = "https://www.sublinks.com";

    try (MockedStatic<Jsoup> jsoupMockedStatic = Mockito.mockStatic(Jsoup.class)) {
      jsoupMockedStatic.when(() -> Jsoup.connect(normalizedUrl)).thenReturn(connection);
      Mockito.when(connection.get()).thenReturn(document);

      Mockito.when(document.select("meta[property=og:title]")).thenReturn(titleElement);
      Mockito.when(document.title()).thenReturn("Title");
      Mockito.when(titleElement.isEmpty()).thenReturn(true);

      Mockito.when(document.select("meta[property=og:description]")).thenReturn(descriptionElement);
      Mockito.when(descriptionElement.isEmpty()).thenReturn(true);

      Mockito.when(document.select("meta[property=og:image]")).thenReturn(imageElement);
      Mockito.when(imageElement.isEmpty()).thenReturn(true);

      Mockito.when(document.select("meta[property=og:video:url]")).thenReturn(videoElement);
      Mockito.when(videoElement.isEmpty()).thenReturn(true);

      SiteMetadata siteMetadata = siteMetadataUtil.fetchSiteMetadata(normalizedUrl);

      assertEquals("Title", siteMetadata.title(), "SiteMetadata title value did not match expected");
      assertNull(siteMetadata.description(), "SiteMetadata description value should be null");
      assertNull(siteMetadata.imageUrl(), "SiteMetadata image value should be null");
      assertNull(siteMetadata.videoUrl(), "SiteMetadata video value should be null");
    }
  }

  @Test
  void givenUrlWithOgTags_whenFetchSiteMetadata_thenReturnSiteMetadataFromOgTags() throws IOException {

    String normalizedUrl = "https://www.sublinks.com";
    String tagTitle = "Title from tag";
    String tagDescription = "Description from tag";
    String tagImage = "Image from tag";
    String tagVideo = "Video from tag";

    try (MockedStatic<Jsoup> jsoupMockedStatic = Mockito.mockStatic(Jsoup.class)) {
      jsoupMockedStatic.when(() -> Jsoup.connect(normalizedUrl)).thenReturn(connection);
      Mockito.when(connection.get()).thenReturn(document);

      Mockito.when(document.select("meta[property=og:title]")).thenReturn(titleElement);
      Mockito.when(document.title()).thenReturn("Title");
      Mockito.when(titleElement.isEmpty()).thenReturn(false);
      Mockito.when(titleElement.first().attr("content")).thenReturn(tagTitle);

      Mockito.when(document.select("meta[property=og:description]")).thenReturn(descriptionElement);
      Mockito.when(descriptionElement.isEmpty()).thenReturn(false);
      Mockito.when(descriptionElement.first().attr("content")).thenReturn(tagDescription);


      Mockito.when(document.select("meta[property=og:image]")).thenReturn(imageElement);
      Mockito.when(imageElement.isEmpty()).thenReturn(false);
      Mockito.when(imageElement.first().attr("content")).thenReturn(tagImage);


      Mockito.when(document.select("meta[property=og:video:url]")).thenReturn(videoElement);
      Mockito.when(videoElement.isEmpty()).thenReturn(false);
      Mockito.when(videoElement.first().attr("content")).thenReturn(tagVideo);

      SiteMetadata siteMetadata = siteMetadataUtil.fetchSiteMetadata(normalizedUrl);

      assertEquals(tagTitle, siteMetadata.title(), "SiteMetadata title value did not match expected");
      assertEquals(tagDescription, siteMetadata.description(), "SiteMetadata description value did not match expected");
      assertEquals(tagImage, siteMetadata.imageUrl(), "SiteMetadata image value did not match expected");
      assertEquals(tagVideo, siteMetadata.videoUrl(), "SiteMetadata video value did not match expected");
    }
  }
}
