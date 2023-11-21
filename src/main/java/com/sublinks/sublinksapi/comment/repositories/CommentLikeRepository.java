package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentLike;
import com.sublinks.sublinksapi.person.dto.Person;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

  Optional<CommentLike> getCommentLikeByPersonAndComment(Person person, Comment comment);
}
