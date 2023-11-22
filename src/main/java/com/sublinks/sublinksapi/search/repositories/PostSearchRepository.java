package com.sublinks.sublinksapi.search.repositories;

import com.sublinks.sublinksapi.post.dto.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostSearchRepository extends JpaRepository<Post, Long> {

   // @Query(value = "SELECT p.*, ppc.* FROM posts p LEFT JOIN post_post_cross_post ppc ON ppc.post_id = p.id WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.post_body) LIKE LOWER(CONCAT('%', :keyword,'%'))",
   //        nativeQuery = true)
   @Query(value = "SELECT p.*, ppc.* FROM posts p LEFT JOIN post_post_cross_post ppc ON ppc.post_id = p.id WHERE MATCH(p.title, p.post_body) AGAINST (CONCAT('*', :keyword, '*') IN BOOLEAN MODE);",
           countQuery = "SELECT COUNT(p.id) FROM posts p WHERE MATCH(p.title, p.post_body) AGAINST (CONCAT('*', :keyword, '*') IN BOOLEAN MODE);",
           nativeQuery = true)
    Page<Post> searchAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
