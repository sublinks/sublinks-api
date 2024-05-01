package com.sublinks.sublinksapi.api.lemmy.v3.community.models;

import lombok.Builder;

/**
 * Represents an action to add a moderator to a community. This class is immutable and implements
 * the record feature in Java.
 */
@Builder
@SuppressWarnings("RecordComponentName")
public record AddModToCommunity(
    Integer community_id,
    Integer person_id,
    Boolean added
) {

}