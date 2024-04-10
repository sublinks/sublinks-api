package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.post.entities.CrossPost;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrossPostRepository extends JpaRepository<CrossPost, Long> {

  Optional<CrossPost> getCrossPostByMd5Hash(String md5Hash);
}
