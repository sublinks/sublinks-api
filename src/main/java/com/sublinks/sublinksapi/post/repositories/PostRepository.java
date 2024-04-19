package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.post.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositorySearch {

}
