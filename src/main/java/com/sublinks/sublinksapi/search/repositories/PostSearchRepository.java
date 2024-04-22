package com.sublinks.sublinksapi.search.repositories;

import com.sublinks.sublinksapi.post.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostSearchRepository extends JpaRepository<Post, Long> {

  @Query(value = "SELECT p.*, ppc.* FROM posts p LEFT JOIN post_post_cross_post ppc ON ppc.post_id = p.id WHERE to_tsquery(:keyword) @@ to_tsvector(p.post_body) OR to_tsquery(:keyword) @@ to_tsvector(p.title);", nativeQuery = true)
  Page<Post> searchAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
