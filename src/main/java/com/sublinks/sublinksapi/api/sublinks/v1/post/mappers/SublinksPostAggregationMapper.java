package com.sublinks.sublinksapi.api.sublinks.v1.post.mappers;

import com.sublinks.sublinksapi.api.sublinks.v1.post.models.PostAggregateResponse;
import com.sublinks.sublinksapi.post.entities.PostAggregate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class SublinksPostAggregationMapper implements
    Converter<PostAggregate, PostAggregateResponse> {

  @Override
  @Mapping(target = "key", source = "postAggregate.id")
  @Mapping(target = "commentCount", source = "postAggregate.commentCount")
  @Mapping(target = "downvoteCount", source = "postAggregate.downvoteCount")
  @Mapping(target = "upvoteCount", source = "postAggregate.upvoteCount")
  @Mapping(target = "score", source = "postAggregate.score")
  @Mapping(target = "hotScore", source = "postAggregate.hotRank")
  @Mapping(target = "controversyScore", source = "postAggregate.controversyScore")
  public abstract PostAggregateResponse convert(@Nullable PostAggregate postAggregate);


}
