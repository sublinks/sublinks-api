package com.sublinks.sublinksapi.privatemessages.repositories;

import com.sublinks.sublinksapi.privatemessages.dto.PrivateMessageReport;
import com.sublinks.sublinksapi.privatemessages.models.PrivateMessageReportSearchCriteria;
import java.util.List;

public interface PrivateMessageReportSearchRepository {

  List<PrivateMessageReport> allPrivateMessageReportsBySearchCriteria(
      PrivateMessageReportSearchCriteria privateMessageSearchCriteria);

  long countAllPrivateMessageReportsByResolvedFalse();

}
