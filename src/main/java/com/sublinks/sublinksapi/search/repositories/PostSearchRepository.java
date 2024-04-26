package com.sublinks.sublinksapi.search.repositories;

import com.sublinks.sublinksapi.post.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostSearchRepository extends JpaRepository<Post, Long> {

  @Query(value = "SELECT p.*, ppcp.* FROM posts p JOIN post_post_cross_post ppcp on p.id = ppcp.post_id WHERE p.search_vector @@ to_tsquery('english', :keyword);", countQuery = "SELECT COUNT(p.id)FROM posts p WHERE p.search_vector @@ to_tsquery('english', :keyword) ;", nativeQuery = true)
  Page<Post> searchAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
