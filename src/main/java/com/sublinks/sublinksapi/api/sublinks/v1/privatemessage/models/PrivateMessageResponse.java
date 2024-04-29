package com.sublinks.sublinksapi.api.sublinks.v1.privatemessage.models;

import com.sublinks.sublinksapi.api.sublinks.v1.person.models.PersonResponse;
import lombok.Builder;

@Builder
public record PrivateMessageResponse(String key,
                                     PersonResponse sender,
                                     PersonResponse recipient,
                                     String content,
                                     boolean isLocal,
                                     boolean isDeleted,
                                     boolean isRead,
                                     String activityPubId,
                                     String createdAt,
                                     String updatedAt) {

}
