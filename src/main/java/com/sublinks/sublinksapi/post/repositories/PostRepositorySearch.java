package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.post.PostSearchCriteria;
import com.sublinks.sublinksapi.post.dto.Post;

import java.util.List;

public interface PostRepositorySearch {
    List<Post> allPostsBySearchCriteria(PostSearchCriteria postSearchCriteria);
}
