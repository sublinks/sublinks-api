package com.sublinks.sublinksapi.private_messages.repositories;

import com.sublinks.sublinksapi.private_messages.dto.PrivateMessage;
import com.sublinks.sublinksapi.private_messages.models.PrivateMessageSearchCriteria;

import java.util.List;

public interface PrivateMessageRepositorySearch {
    List<PrivateMessage> allPrivateMessagesBySearchCriteria(PrivateMessageSearchCriteria privateMessageSearchCriteria);
}
