package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.post.dto.PostReport;
import com.sublinks.sublinksapi.post.models.PostReportSearchCriteria;
import jakarta.annotation.Nullable;
import java.util.List;

public interface PostReportRepositorySearch {

  List<PostReport> allPostReportsBySearchCriteria(
      PostReportSearchCriteria postReportSearchCriteria);

  long countAllPostReportsByResolvedFalseAndCommunity(@Nullable List<Community> communities);

  long countAllPostReportsReportsByResolvedFalse();
}
