package com.sublinks.sublinksapi.comment;

import com.sublinks.sublinksapi.instance.Instance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<Instance, Long> {

}
