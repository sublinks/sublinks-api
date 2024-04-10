package com.sublinks.sublinksapi.post.repositories;

import com.sublinks.sublinksapi.post.entities.PostAggregate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostAggregateRepository extends JpaRepository<PostAggregate, Long> {

}
