package com.sublinks.sublinksapi.utils;

import java.io.IOException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiteMetadataUtil {

  public SiteMetadata fetchSiteMetadata(String normalizedUrl) {

    Document doc = null;
    try {
      doc = Jsoup.connect(normalizedUrl).get();
    } catch (IOException e) {
      return SiteMetadata.builder().build();
    }

    Elements metaOgTitle = doc.select("meta[property=og:title]");
    String title = doc.title();
    if (!metaOgTitle.isEmpty()) {
      title = metaOgTitle.first().attr("content");
    }

    Elements metaOgDescription = doc.select("meta[property=og:description]");
    String description = null;
    if (!metaOgDescription.isEmpty()) {
      description = metaOgDescription.first().attr("content");
    }

    Elements metaOgImage = doc.select("meta[property=og:image]");
    String image = null;
    if (!metaOgImage.isEmpty()) {
      image = metaOgImage.first().attr("content");
    }

    Elements metaOgVideo = doc.select("meta[property=og:video:url]");
    String video = null;
    if (!metaOgVideo.isEmpty()) {
      video = metaOgVideo.first().attr("content");
    }

    return new SiteMetadata(title, description, image, video);
  }

  @Builder
  public record SiteMetadata(
      String title,
      String description,
      String imageUrl,
      String videoUrl
  ) {

  }
}
