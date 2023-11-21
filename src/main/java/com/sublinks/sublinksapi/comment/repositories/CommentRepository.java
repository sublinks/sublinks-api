package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.dto.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositorySearch {

}
