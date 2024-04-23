package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.post.entities.Post;
import com.sublinks.sublinksapi.post.entities.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostReportRepository extends JpaRepository<PostReport, Long>,
    PostReportRepositorySearch {


  @Modifying
  @Query("UPDATE PostReport pr SET pr.resolved = true, pr.resolver = :resolver WHERE pr.resolved = false AND pr.post = :post")
  void resolveAllReportsByPost(@Param("post") Post post, @Param("resolver") Person resolver);
}
