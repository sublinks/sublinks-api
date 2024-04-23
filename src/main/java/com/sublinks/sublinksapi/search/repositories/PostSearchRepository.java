package com.sublinks.sublinksapi.search.repositories;

import com.sublinks.sublinksapi.post.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostSearchRepository extends JpaRepository<Post, Long> {

  @Query(value = "SELECT p.* FROM posts p WHERE p.title_search @@ to_tsquery(:keyword) OR p.title_slug_search @@ to_tsquery(:keyword) OR p.post_body_search @@ to_tsquery(:keyword);", countQuery = "SELECT COUNT(p.id)FROM posts p WHERE p.title_search @@ to_tsquery(:keyword) OR p.title_slug_search @@ to_tsquery(:keyword) OR p.post_body_search @@ to_tsquery(:keyword);", nativeQuery = true)
  Page<Post> searchAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
