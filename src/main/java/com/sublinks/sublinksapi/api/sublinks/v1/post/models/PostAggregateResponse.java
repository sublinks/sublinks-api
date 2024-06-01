package com.sublinks.sublinksapi.api.sublinks.v1.post.models;

public record PostAggregateResponse(
    String key,
    String commentCount,
    String downvoteCount,
    String upvoteCount,
    String score,
    String hotScore,
    String controversyScore) {

}
