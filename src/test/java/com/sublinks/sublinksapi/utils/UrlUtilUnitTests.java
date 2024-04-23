package com.sublinks.sublinksapi.utils;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UrlUtilUnitTests {

  UrlUtil urlUtil = new UrlUtil();

  String httpsUrl = "https://www.sublinks.com";
  String httpUrl = "http://www.sublinks.com";
  String urlWithJunkProtocol = "1234://www.sublinks.com?utm_source=Content";
  // Making this list package private in UrlUtil.java would allow this class access and make this a little cleaner
  Collection<String> spyList = List.of("utm_source", "utm_medium", "utm_campaign", "utm_term",
      "utm_content", "gclid", "gclsrc", "dclid", "fbclid");
  Collection<String> realParameters = List.of("real_query=real", "another_real_query=real", "last_real_query=real");
  String mixOfParameters = "?real_query=real&utm_source=Source&utm_campaign=Campaign"
        + "&utm_term=Term&utm_content=Content&another_real_query=real&gclid=gclid&gclsrc=gclsrc"
        + "&dclid=dclid&fbclid=fbclid&last_real_query=real";
  String trackingParameters = "?utm_term=Term&utm_content=Content";


  @Test
  void givenUrlWithJunkProtocol_whenNormalizeUrl_thenReturnUnmodifiedUrl() {

    String urlWithJunkProtocolAndTrackers = urlWithJunkProtocol + trackingParameters;

    String normalizedUrl = urlUtil.normalizeUrl(urlWithJunkProtocolAndTrackers);

    assertEquals(urlWithJunkProtocolAndTrackers, normalizedUrl, "URL should not have been changed");
  }

  @Test
  void givenHttpUrlWithNoParameters_whenNormalizeUrl_thenReturnUnmodifiedUrl() {

    String normalizedUrl = urlUtil.normalizeUrl(httpUrl);

    assertEquals(httpUrl, normalizedUrl, "URL should not have been changed");
  }

  @Test
  void givenHttpsUrlWithNoParameters_whenNormalizeUrl_thenReturnUnmodifiedUrl() {

    String normalizedUrl = urlUtil.normalizeUrl(httpsUrl);

    assertEquals(httpsUrl, normalizedUrl, "URL should not have been changed");
  }

  @Test
  void givenHttpUrlWithOnlyTrackingParameters_whenNormalizeUrl_thenReturnUrlWithNoParameters() {

    String urlWithOnlyTrackingParameters = httpUrl + trackingParameters;

    String normalizedUrl = urlUtil.normalizeUrl(urlWithOnlyTrackingParameters);

    assertEquals(httpUrl, normalizedUrl,
        "Url with only tracking parameters was not returned with no parameters");
  }

  @Test
  void givenHttpsUrlWithOnlyTrackingParameters_whenNormalizeUrl_thenReturnUrlWithNoParameters() {

    String urlWithOnlyTrackingParameters = httpsUrl + trackingParameters;

    String normalizedUrl = urlUtil.normalizeUrl(urlWithOnlyTrackingParameters);

    assertEquals(httpsUrl, normalizedUrl,
        "Url with only tracking parameters was not returned with no parameters");
  }

  @Test
  void givenHttpUrlWithRealParameters_whenNormalizeUrl_thenReturnUrlWithParameters() {

    String urlWithRealParameters = httpUrl + "?" + String.join("&", realParameters);

    String normalizedUrl = urlUtil.normalizeUrl(urlWithRealParameters);

    assertTrue(realParameters.stream().allMatch(normalizedUrl::contains),
        "Normalized URL removed non-tracking parameters");
    assertTrue(normalizedUrl.contains(httpUrl + "?"),
        "Normalized URL missing scheme/path info");
  }

  @Test
  void givenHttpsUrlWithRealParameters_whenNormalizeUrl_thenReturnUrlWithParameters() {

    String urlWithRealParameters = httpsUrl + "?" + String.join("&", realParameters);

    String normalizedUrl = urlUtil.normalizeUrl(urlWithRealParameters);

    assertTrue(realParameters.stream().allMatch(normalizedUrl::contains),
        "Normalized URL removed non-tracking parameters");
    assertTrue(normalizedUrl.contains(httpsUrl + "?"),
        "Normalized URL missing scheme/path info");
  }

  @Test
  void givenHttpUrlWithMixOfParameters_whenNormalizeUrl_thenReturnUrlWithWithoutTrackingParameters() {

    String urlWithTrackers = httpUrl + mixOfParameters;

    String normalizedUrl = urlUtil.normalizeUrl(urlWithTrackers);

    // Parameters are stored in a HashMap, meaning order cannot be guaranteed. Therefore, we're
    // simply storing the parameters that should/shouldn't be there in a list to check against.
    assertFalse(spyList.stream().anyMatch(normalizedUrl::contains),
        "Normalized URL contained tracking parameters");
    assertTrue(realParameters.stream().allMatch(normalizedUrl::contains),
        "Normalized URL removed non-tracking parameters");
    assertTrue(normalizedUrl.contains(httpUrl + "?"),
        "Normalized URL missing scheme/path info");
  }

  @Test
  void givenHttpsUrlWithMixOfParameters_whenNormalizeUrl_thenReturnUrlWithWithoutTrackingParameters() {

    String urlWithTrackers = httpsUrl + mixOfParameters;

    String normalizedUrl = urlUtil.normalizeUrl(urlWithTrackers);

    // Parameters are stored in a HashMap, meaning order cannot be guaranteed. Therefore, we're
    // simply storing the parameters that should/shouldn't be there in a list to check against.
    assertFalse(spyList.stream().anyMatch(normalizedUrl::contains),
        "Normalized URL contained tracking parameters");
    assertTrue(realParameters.stream().allMatch(normalizedUrl::contains),
        "Normalized URL removed non-tracking parameters");
    assertTrue(normalizedUrl.contains(httpsUrl + "?"),
        "Normalized URL missing scheme/path info");
  }

  @Test
  void givenValidHttpUrl_whenCheckUrlProtocol_thenDoNothing() {

    assertDoesNotThrow(() -> urlUtil.checkUrlProtocol(httpUrl));
  }

  @Test
  void givenValidHttpsUrl_whenCheckUrlProtocol_thenDoNothing() {

    assertDoesNotThrow(() -> urlUtil.checkUrlProtocol(httpsUrl));
  }

  @Test
  @Disabled // URL does not provide a protocol handler for magnet by default
  void givenValidMagnetUrl_whenCheckUrlProtocol_thenDoNothing() {

    String providedMagnet = "magnet:?xt=urn:btih:5dee65101db281ac9c46344cd6b175cdcad53426&dn=download";

    assertDoesNotThrow(() -> urlUtil.checkUrlProtocol(providedMagnet));
  }

  @Test
  void givenInvalidProtocol_whenCheckUrlProtocol_thenThrowRuntimeException() {

    Exception exception = assertThrows(RuntimeException.class, () -> urlUtil.checkUrlProtocol(urlWithJunkProtocol));

    assertEquals("Invalid URL Scheme", exception.getMessage());
  }
}
