package com.sublinks.sublinksapi.community.repositories;

import com.sublinks.sublinksapi.community.entities.Community;
import com.sublinks.sublinksapi.community.models.CommunitySearchCriteria;
import java.util.List;

public interface CommunitySearchRepository {

  List<Community> allCommunitiesBySearchCriteria(CommunitySearchCriteria communitySearchCriteria);
}
