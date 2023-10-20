package com.sublinksapp.sublinksappapi.post;

import com.sublinksapp.sublinksappapi.instance.Instance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<Instance, Long> {

}
