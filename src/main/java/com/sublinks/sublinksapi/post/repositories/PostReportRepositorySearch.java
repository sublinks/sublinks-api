package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.post.entities.PostReport;
import com.sublinks.sublinksapi.post.models.PostReportSearchCriteria;
import jakarta.annotation.Nullable;
import java.util.List;

public interface PostReportRepositorySearch {

  List<PostReport> allPostReportsBySearchCriteria(
      PostReportSearchCriteria postReportSearchCriteria);

  long countAllPostReportsByResolvedFalseAndCommunity(@Nullable List<Community> communities);

  long countAllPostReportsReportsByResolvedFalse();

  void resolveAllPostReportsByPerson(Person person, Person resolver);

  void resolveAllPostReportsByPersonAndCommunity(Person person, Community community, Person resolver);

}
