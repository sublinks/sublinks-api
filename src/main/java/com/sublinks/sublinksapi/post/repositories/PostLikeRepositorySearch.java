package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.post.entities.PostLike;
import com.sublinks.sublinksapi.post.models.PostLikeSearchCriteria;
import java.util.List;

public interface PostLikeRepositorySearch {

  List<PostLike> allPostLikesBySearchCriteria(
      PostLikeSearchCriteria commentLikeSearchCriteria);

}
