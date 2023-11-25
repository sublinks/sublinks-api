package com.sublinks.sublinksapi.privatemessages.repositories;

import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.privatemessages.dto.PrivateMessageReport;
import com.sublinks.sublinksapi.privatemessages.models.PrivateMessageReportSearchCriteria;
import jakarta.annotation.Nullable;
import java.util.List;

public interface PrivateMessageReportSearchRepository {

  List<PrivateMessageReport> allPrivateMessageReportsBySearchCriteria(
      PrivateMessageReportSearchCriteria privateMessageSearchCriteria);

  long countAllPrivateMessageReportsByResolvedFalseAndCommunity(@Nullable Community community);

  long countAllPrivateMessageReportsByResolvedFalse();

}
