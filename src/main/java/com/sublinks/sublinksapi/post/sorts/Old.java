package com.sublinks.sublinksapi.post.sorts;

import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.entities.Post_;
import com.sublinks.sublinksapi.post.services.PostSearchQueryService.Builder;
import jakarta.persistence.criteria.Predicate;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class Old implements SortingTypeInterface {

  @Override
  public void applySorting(Builder builder) {

    builder.getCriteriaQuery()
        .orderBy(builder.getCriteriaBuilder()
            .asc(builder.getPostTable().get("createdAt")));
  }

  @Override
  public void applyCursor(Builder builder) {

    List<Predicate> predicates = builder.getPredicates();
    predicates.add(
        builder.getCriteriaBuilder()
            .greaterThanOrEqualTo(builder.getPostTable().get(Post_.ID), builder.getCursor())
    );
  }

  @Override
  public String getCursor(List<Post> posts) {

    Post lastPost = posts.get(posts.size() - 1);
    String originalInput = lastPost.getId().toString();
    String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
    return URLEncoder.encode(encodedString, StandardCharsets.UTF_8);
  }
}
