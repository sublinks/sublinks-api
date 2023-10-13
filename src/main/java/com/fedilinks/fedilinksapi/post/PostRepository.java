package com.fedilinks.fedilinksapi.post;

import com.fedilinks.fedilinksapi.instance.Instance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Instance, Long> {

}
