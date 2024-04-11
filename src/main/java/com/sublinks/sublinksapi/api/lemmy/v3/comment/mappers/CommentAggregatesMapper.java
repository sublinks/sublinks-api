package com.sublinks.sublinksapi.api.lemmy.v3.comment.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.comment.models.CommentAggregates;
import com.sublinks.sublinksapi.comment.entities.CommentAggregate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentAggregatesMapper extends Converter<CommentAggregate, CommentAggregates> {

  @Override
  @Mapping(target = "upvotes", source = "commentAggregate.upVotes")
  @Mapping(target = "score", source = "commentAggregate.score")
  @Mapping(target = "published", source = "commentAggregate.createdAt",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "hot_rank", source = "commentAggregate.hotRank")
  @Mapping(target = "downvotes", source = "commentAggregate.downVotes")
  @Mapping(target = "comment_id", source = "commentAggregate.comment.id")
  @Mapping(target = "child_count", source = "commentAggregate.childrenCount")
  CommentAggregates convert(@Nullable CommentAggregate commentAggregate);
}
