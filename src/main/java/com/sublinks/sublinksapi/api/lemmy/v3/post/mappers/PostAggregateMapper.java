package com.sublinks.sublinksapi.api.lemmy.v3.post.mappers;

import com.sublinks.sublinksapi.api.lemmy.v3.post.models.PostAggregates;
import com.sublinks.sublinksapi.api.lemmy.v3.utils.DateUtils;
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
  @Mapping(target = "published", source = "post.createdAt",
      dateFormat = DateUtils.FRONT_END_DATE_FORMAT)
  @Mapping(target = "post_id", source = "postAggregate.post.id")
  @Mapping(target = "downvotes", source = "postAggregate.downVoteCount")
  @Mapping(target = "comments", source = "postAggregate.commentCount")
  PostAggregates convert(@Nullable PostAggregate postAggregate);
}
