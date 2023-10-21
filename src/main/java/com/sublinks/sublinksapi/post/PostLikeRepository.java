package com.sublinks.sublinksapi.post;

import com.sublinks.sublinksapi.instance.Instance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<Instance, Long> {

}
