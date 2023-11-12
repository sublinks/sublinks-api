package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.post.dto.CrossPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CrossPostRepository extends JpaRepository<CrossPost, Long> {
    Optional<CrossPost> getCrossPostByMd5Hash(String md5Hash);
}
