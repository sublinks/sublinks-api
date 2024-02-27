package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PostHistoryRepository extends JpaRepository<PostHistory, Long>, PostHistoryRepositoryExtended {

  List<PostHistory> findByPostOrderByCreatedAtAsc(Post post);

  Optional<PostHistory> findFirstByPostOrderByCreatedAtDesc(Post post);

  int deleteAllByPost(Post post);
}
