package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.post.dto.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostReportRepository extends JpaRepository<PostReport, Long>,
    PostReportRepositorySearch {


}
