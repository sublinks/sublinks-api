package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.community.dto.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositorySearch {

}
