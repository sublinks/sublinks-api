package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.models.PostSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepositorySearch {
    List<Post> allPostsBySearchCriteria(PostSearchCriteria postSearchCriteria);
}
