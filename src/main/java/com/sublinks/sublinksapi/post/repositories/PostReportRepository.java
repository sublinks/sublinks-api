package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.post.dto.Post;
import com.sublinks.sublinksapi.post.dto.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PostReportRepository extends JpaRepository<PostReport, Long>,
    PostReportRepositorySearch {


  @Modifying
  @Query("UPDATE PostReport pr SET pr.resolved = true, pr.resolver = :resolver WHERE pr.resolved = false AND pr.post = :post")
  void resolveAllReportsByPost(@Param("post") Post post, @Param("resolver") Person resolver);
}
