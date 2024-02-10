package com.sublinks.sublinksapi.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BaseUrlUtil {
  @Value("${sublinks.settings.base_url}")
  String baseUrl;

  public String getBaseUrl() {
    // Normalize url
    if (baseUrl.endsWith("/")) {
      baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
    }

    return baseUrl;
  }
}
