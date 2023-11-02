package com.sublinks.sublinksapi.api.lemmy.v3.post.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostAggregates;
import com.sublinks.sublinksapi.post.dto.PostAggregate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostAggregateMapper extends Converter<PostAggregate, PostAggregates> {
    @Override
    @Mapping(target = "upvotes", source = "postAggregate.upVoteCount")
    @Mapping(target = "score", source = "postAggregate.score")
    @Mapping(target = "published", source = "post.createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "post_id", source = "postAggregate.post.id")
    @Mapping(target = "newest_comment_time_necro", source = "post.updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "newest_comment_time", source = "post.updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @Mapping(target = "hot_rank_active", source = "postAggregate.hotRankActive")
    @Mapping(target = "hot_rank", source = "postAggregate.hotRank")
    @Mapping(target = "featured_local", source = "post.featured")
    @Mapping(target = "featured_community", source = "post.featuredInCommunity")
    @Mapping(target = "downvotes", source = "postAggregate.downVoteCount")
    @Mapping(target = "comments", source = "postAggregate.commentCount")
    PostAggregates convert(@Nullable PostAggregate postAggregate);
}
