package com.sublinks.sublinksapi.api.sublinks.v1.comment.models;

import com.sublinks.sublinksapi.api.sublinks.v1.person.models.PersonResponse;
import java.util.List;
import lombok.Builder;

@Builder(toBuilder = true)
public record CommentResponse(
    String key,
    String activityPubId,
    String body,
    String path,
    Boolean isLocal,
    Boolean isDeleted,
    Boolean isFeatured,
    Boolean isRemoved,
    String createdAt,
    PersonResponse creator,
    List<CommentResponse> replies,
    String updatedAt) {


  public String getId() {

    List<String> ids = List.of(key.split("\\."));
    return ids.get(ids.size() - 1);
  }

  public String getParentKey() {

    List<String> ids = List.of(key.split("\\."));
    return ids.size() > 1 ? String.join(".", ids.subList(0, ids.size() - 1)) : null;
  }
}
