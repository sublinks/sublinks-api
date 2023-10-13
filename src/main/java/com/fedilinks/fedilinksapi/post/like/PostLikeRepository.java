package com.fedilinks.fedilinksapi.post.like;

import com.fedilinks.fedilinksapi.instance.Instance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<Instance, Long> {

}
