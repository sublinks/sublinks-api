package com.sublinks.sublinksapi.api.lemmy.v3.comment.models;

import com.sublinks.sublinksapi.api.lemmy.v3.user.models.Person;
import lombok.Builder;
import java.util.List;

@Builder
public record VoteView(
    Person creator,
    int score
) {

}