package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;

/**
 * Represents a vote view, containing information about the creator and the score.
 */
@Builder
public record VoteView(
    Person creator,
    Integer score
) {

}