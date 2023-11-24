package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.post.dto.PostReport;
import com.sublinks.sublinksapi.post.models.PostReportSearchCriteria;

import java.util.List;

public interface PostReportRepositorySearch {

  List<PostReport> allPostReportsBySearchCriteria(
      PostReportSearchCriteria postReportSearchCriteria);
}
