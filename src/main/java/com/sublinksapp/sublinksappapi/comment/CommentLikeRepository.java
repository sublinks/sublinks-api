package com.sublinksapp.sublinksappapi.comment;

import com.sublinksapp.sublinksappapi.instance.Instance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<Instance, Long> {

}
