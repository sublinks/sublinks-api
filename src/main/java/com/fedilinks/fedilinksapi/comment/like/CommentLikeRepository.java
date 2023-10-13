package com.fedilinks.fedilinksapi.comment.like;

import com.fedilinks.fedilinksapi.instance.Instance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<Instance, Long> {

}
