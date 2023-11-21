package com.sublinks.sublinksapi.privatemessages.repositories;

import com.sublinks.sublinksapi.privatemessages.dto.PrivateMessage;
import com.sublinks.sublinksapi.privatemessages.models.PrivateMessageSearchCriteria;
import java.util.List;

public interface PrivateMessageRepositorySearch {

  List<PrivateMessage> allPrivateMessagesBySearchCriteria(
      PrivateMessageSearchCriteria privateMessageSearchCriteria);
}
