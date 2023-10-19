/**
  Comments table
 */
alter table comments
    add foreign key (language_id) references `languages` (id) on delete cascade,
    add foreign key (person_id) references people (id) on delete cascade,
    add foreign key (community_id) references communities (id) on delete cascade,
    add foreign key (post_id) references posts (id) on delete cascade;

/**
  Comment Aggregates table
 */
alter table comment_aggregates
    add foreign key (comment_id) references comments (id) on delete cascade;

/**
  Comment Likes table
 */
alter table comment_likes
    add foreign key (person_id) references people (id) on delete cascade,
    add foreign key (comment_id) references comments (id) on delete cascade;

/**
  Communities table
 */
alter table communities
    add foreign key (instance_id) references instances (id) on delete cascade;

/**
  Community Languages table
 */
alter table community_languages
    add foreign key (community_id) references communities (id) on delete cascade,
    add foreign key (language_id) references languages (id) on delete cascade;

/**
  Community Aggregates table
 */
alter table community_aggregates
    add foreign key (community_id) references communities (id) on delete cascade;

/**
  Instance Aggregates table
 */
alter table instance_aggregates
    add foreign key (instance_id) references instances (id) on delete cascade;

/**
  Instance Languages table
 */
alter table instance_languages
    add foreign key (instance_id) references instances (id) on delete cascade,
    add foreign key (language_id) references languages (id) on delete cascade;


/**
  People table
 */
alter table people
    add foreign key (instance_id) references instances (id) on delete cascade;

/**
  Person Aggregates table
 */
alter table person_aggregates
    add foreign key (person_id) references people (id) on delete cascade;
/**
  Link Person Communities table
 */
alter table link_person_communities
    add foreign key (person_id) references people (id) on delete cascade,
    add foreign key (community_id) references communities (id) on delete cascade;

/**
  Person Languages table
 */
alter table person_languages
    add foreign key (person_id) references people (id) on delete cascade,
    add foreign key (language_id) references languages (id) on delete cascade;

/**
  Posts table
 */
alter table posts
    add foreign key (instance_id) references instances (id) on delete cascade,
    add foreign key (language_id) references languages (id) on delete cascade,
    add foreign key (community_id) references communities (id) on delete cascade;

/**
  Post Aggregates table
 */
alter table post_aggregates
    add foreign key (post_id) references posts (id) on delete cascade,
    add foreign key (community_id) references communities (id) on delete cascade;

/**
  Posts Likes table
 */
alter table post_likes
    add foreign key (post_id) references posts (id) on delete cascade;