package com.sublinks.sublinksapi.post.sorts;

import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.services.PostSearchQueryService.Builder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class TopDay implements SortingTypeInterface {

  @Override
  public void applySorting(Builder builder) {

    builder.getCriteriaQuery()
        .orderBy(builder.getCriteriaBuilder()
            .asc(builder.getPostTable().get("createdAt")));
  }

  @Override
  public void applyCursor(Builder builder) {

  }

  @Override
  public String getCursor(List<Post> posts) {

    Post lastPost = posts.get(posts.size() - 1);
    String originalInput = lastPost.getId().toString();
    String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
    return URLEncoder.encode(encodedString, StandardCharsets.UTF_8);
  }
}
