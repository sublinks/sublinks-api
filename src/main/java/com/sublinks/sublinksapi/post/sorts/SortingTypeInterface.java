package com.sublinks.sublinksapi.post.sorts;

import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.services.PostSearchQueryService.Builder;
import java.util.List;

public interface SortingTypeInterface {

  void applySorting(Builder builder);

  void applyCursor(Builder builder);

  String getCursor(List<Post> posts);
}
