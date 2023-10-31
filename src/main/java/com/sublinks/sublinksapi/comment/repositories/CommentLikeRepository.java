package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.instance.dto.Instance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<Instance, Long> {

}
