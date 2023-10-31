package com.sublinks.sublinksapi.post;

import java.util.List;

public interface PostRepositorySearch {
    List<Post> allPostsBySearchCriteria(PostSearchCriteria postSearchCriteria);
}
