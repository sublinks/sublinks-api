package com.sublinks.sublinksapi.search.repositories;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.post.dto.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentSearchRepository extends JpaRepository<Comment, Long> {

   // @Query(value = "SELECT p.*, ppc.* FROM posts p LEFT JOIN post_post_cross_post ppc ON ppc.post_id = p.id WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.post_body) LIKE LOWER(CONCAT('%', :keyword,'%'))",
   //        nativeQuery = true)
   @Query(value = "SELECT c.* FROM comments c LEFT JOIN posts p on p.id = c.post_id LEFT JOIN people pp ON pp.id = c.person_id LEFT JOIN communities cm ON cm.id = c.community_id WHERE MATCH(c.comment_body) AGAINST (CONCAT('*', :keyword, '*') IN BOOLEAN MODE);",
           countQuery = "SELECT COUNT(c.id) FROM comments c WHERE MATCH(c.comment_body) AGAINST (CONCAT('*', :keyword, '*') IN BOOLEAN MODE);",
           nativeQuery = true)
    Page<Comment> searchAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
