/**
  Comment aggregates table
 */
CREATE TABLE `comment_aggregates`
(
    `id`               BIGINT AUTO_INCREMENT PRIMARY KEY,
    `comment_id`       BIGINT                                    NOT NULL,
    `up_votes`         INT          DEFAULT 0                    NOT NULL,
    `down_votes`       INT          DEFAULT 0                    NOT NULL,
    `score`            INT          DEFAULT 0                    NOT NULL,
    `children_count`   INT          DEFAULT 0                    NOT NULL,
    `hot_rank`         INT          DEFAULT 0                    NOT NULL,
    `controversy_rank` INT          DEFAULT 0                    NOT NULL,
    `created_at`       TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE UNIQUE INDEX `IDX_COMMENT_AGGREGATES_COMMENT_ID` ON `comment_aggregates` (`comment_id`);

/**
  Community aggregates table
 */
CREATE TABLE `community_aggregates`
(
    `id`                          BIGINT AUTO_INCREMENT PRIMARY KEY,
    `community_id`                BIGINT                                    NOT NULL,
    `subscriber_count`            INT          DEFAULT 0                    NOT NULL,
    `post_count`                  INT          DEFAULT 0                    NOT NULL,
    `comment_count`               INT          DEFAULT 0                    NOT NULL,
    `active_daily_user_count`     INT          DEFAULT 0                    NOT NULL,
    `active_weekly_user_count`    INT          DEFAULT 0                    NOT NULL,
    `active_monthly_user_count`   INT          DEFAULT 0                    NOT NULL,
    `active_half_year_user_count` INT          DEFAULT 0                    NOT NULL,
    `hot_rank`                    INT          DEFAULT 0                    NOT NULL,
    `created_at`                  TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE UNIQUE INDEX `IDX_COMMUNITY_AGGREGATES_COMMUNITY_ID` ON `community_aggregates` (`community_id`);


/**
  Person aggregates table
 */
CREATE TABLE `person_aggregates`
(
    `id`            BIGINT AUTO_INCREMENT PRIMARY KEY,
    `person_id`     BIGINT                                    NOT NULL,
    `post_count`    INT          DEFAULT 0                    NOT NULL,
    `comment_count` INT          DEFAULT 0                    NOT NULL,
    `post_score`    INT          DEFAULT 0                    NOT NULL,
    `comment_score` INT          DEFAULT 0                    NOT NULL,
    `created_at`    TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE UNIQUE INDEX `IDX_PERSON_AGGREGATES_PERSON_ID` ON `person_aggregates` (`person_id`);

/**
  Post aggregates table
 */
CREATE TABLE `post_aggregates`
(
    `id`               BIGINT AUTO_INCREMENT PRIMARY KEY,
    `post_id`          BIGINT                                    NOT NULL,
    `community_id`     BIGINT                                    NOT NULL,
    `comment_count`    INT          DEFAULT 0                    NOT NULL,
    `down_vote_count`  INT          DEFAULT 0                    NOT NULL,
    `up_vote_count`    INT          DEFAULT 0                    NOT NULL,
    `score`            INT          DEFAULT 0                    NOT NULL,
    `hot_rank`         INT          DEFAULT 0                    NOT NULL,
    `hot_rank_active`  INT          DEFAULT 0                    NOT NULL,
    `controversy_rank` INT          DEFAULT 0                    NOT NULL,
    `created_at`       TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE UNIQUE INDEX `IDX_POST_AGGREGATES_POST_ID` ON `post_aggregates` (`post_id`);
CREATE INDEX `IDX_POST_AGGREGATES_COMMUNITY_ID` ON `post_aggregates` (`community_id`);

/**
  Instance aggregates table
 */
CREATE TABLE `instance_aggregates`
(
    `id`                          BIGINT AUTO_INCREMENT PRIMARY KEY,
    `instance_id`                 BIGINT                                    NOT NULL,
    `user_count`                  INT          DEFAULT 0                    NOT NULL,
    `post_count`                  INT          DEFAULT 0                    NOT NULL,
    `comment_count`               INT          DEFAULT 0                    NOT NULL,
    `community_count`             INT          DEFAULT 0                    NOT NULL,
    `active_daily_user_count`     INT          DEFAULT 0                    NOT NULL,
    `active_weekly_user_count`    INT          DEFAULT 0                    NOT NULL,
    `active_monthly_user_count`   INT          DEFAULT 0                    NOT NULL,
    `active_half_year_user_count` INT          DEFAULT 0                    NOT NULL,
    `created_at`                  TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE UNIQUE INDEX `IDX_INSTANCE_AGGREGATES_POST_ID` ON `instance_aggregates` (`instance_id`);
