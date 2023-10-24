/**
  Comment aggregates table
 */
create table comment_aggregates
(
    `id`               bigint auto_increment primary key,
    `comment_id`       bigint                                    not null,
    `up_votes`         int          default 0                    not null,
    `down_votes`       int          default 0                    not null,
    `score`            int          default 0                    not null,
    `children_count`   int          default 0                    not null,
    `hot_rank`         int          default 0                    not null,
    `controversy_rank` int          default 0                    not null,
    `created_at`       timestamp(3) default current_timestamp(3) not null
) engine = InnoDB
  default charset `utf8mb4`
  collate = 'utf8mb4_unicode_ci';

create index `IDX_COMMENT_AGGREGATES_COMMENT_ID` on `comment_aggregates` (`comment_id`);

/**
  Community aggregates table
 */
create table community_aggregates
(
    `id`                          bigint auto_increment primary key,
    `community_id`                bigint                                    not null,
    `subscriber_count`            int          default 0                    not null,
    `post_count`                  int          default 0                    not null,
    `comment_count`               int          default 0                    not null,
    `active_daily_user_count`     int          default 0                    not null,
    `active_weekly_user_count`    int          default 0                    not null,
    `active_monthly_user_count`   int          default 0                    not null,
    `active_half_year_user_count` int          default 0                    not null,
    `hot_rank`                    int          default 0                    not null,
    `created_at`                  timestamp(3) default current_timestamp(3) not null
) engine = InnoDB
  default charset `utf8mb4`
  collate = 'utf8mb4_unicode_ci';

create index `IDX_COMMUNITY_AGGREGATES_COMMUNITY_ID` on `community_aggregates` (`community_id`);


/**
  Person aggregates table
 */
create table person_aggregates
(
    `id`            bigint auto_increment primary key,
    `person_id`     bigint                                    not null,
    `post_count`    int          default 0                    not null,
    `comment_count` int          default 0                    not null,
    `post_score`    int          default 0                    not null,
    `comment_score` int          default 0                    not null,
    `created_at`    timestamp(3) default current_timestamp(3) not null
) engine = InnoDB
  default charset `utf8mb4`
  collate = 'utf8mb4_unicode_ci';

create index `IDX_PERSON_AGGREGATES_PERSON_ID` on `person_aggregates` (`person_id`);

/**
  Post aggregates table
 */
create table post_aggregates
(
    `id`               bigint auto_increment primary key,
    `post_id`          bigint                                    not null,
    `community_id`     bigint                                    not null,
    `comment_count`    int          default 0                    not null,
    `down_vote_count`  int          default 0                    not null,
    `up_vote_count`    int          default 0                    not null,
    `score`            int          default 0                    not null,
    `hot_rank`         int          default 0                    not null,
    `hot_rank_active`  int          default 0                    not null,
    `controversy_rank` int          default 0                    not null,
    `created_at`       timestamp(3) default current_timestamp(3) not null
) engine = InnoDB
  default charset `utf8mb4`
  collate = 'utf8mb4_unicode_ci';

create index `IDX_POST_AGGREGATES_POST_ID` on `post_aggregates` (`post_id`);
create index `IDX_POST_AGGREGATES_COMMUNITY_ID` on `post_aggregates` (`community_id`);

/**
  Instance aggregates table
 */
create table instance_aggregates
(
    `id`                          bigint auto_increment primary key,
    `instance_id`                 bigint                                    not null,
    `user_count`                  int          default 0                    not null,
    `post_count`                  int          default 0                    not null,
    `comment_count`               int          default 0                    not null,
    `community_count`             int          default 0                    not null,
    `active_daily_user_count`     int          default 0                    not null,
    `active_weekly_user_count`    int          default 0                    not null,
    `active_monthly_user_count`   int          default 0                    not null,
    `active_half_year_user_count` int          default 0                    not null,
    `created_at`                  timestamp(3) default current_timestamp(3) not null
) engine = InnoDB
  default charset `utf8mb4`
  collate = 'utf8mb4_unicode_ci';

create index `IDX_INSTANCE_AGGREGATES_POST_ID` on `instance_aggregates` (`instance_id`);
