/**
  Comments table
 */
CREATE TABLE `comments`
(
    `id`              BIGINT AUTO_INCREMENT PRIMARY KEY,
    `activity_pub_id` TEXT    NOT NULL,
    `language_id`     BIGINT  NOT NULL,
    `is_deleted`      TINYINT NOT NULL DEFAULT 0,
    `is_removed`      TINYINT NOT NULL DEFAULT 0,
    `is_local`        TINYINT NOT NULL DEFAULT 0,
    `person_id`       BIGINT  NOT NULL,
    `community_id`    BIGINT  NOT NULL,
    `post_id`         BIGINT  NOT NULL,
    `is_featured`     TINYINT NOT NULL DEFAULT 0,
    `comment_body`    TEXT,
    `path`            VARCHAR(512),
    `created_at`      TIMESTAMP(3)     DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
    `updated_at`      TIMESTAMP(3)     DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_COMMENTS_LANGUAGE_ID` ON `comments` (`language_id`);
CREATE INDEX `IDX_COMMENTS_PERSON_ID` ON `comments` (`person_id`);
CREATE INDEX `IDX_COMMENTS_COMMUNITY_ID` ON `comments` (`community_id`);
CREATE INDEX `IDX_COMMENTS_POST_ID` ON `comments` (`post_id`);

/**
  Comment Likes table
 */
CREATE TABLE `comment_likes`
(
    `id`           BIGINT AUTO_INCREMENT PRIMARY KEY,
    `post_id`      BIGINT  NOT NULL,
    `person_id`    BIGINT  NOT NULL,
    `comment_id`   BIGINT  NOT NULL,
    `is_up_vote`   TINYINT NOT NULL DEFAULT 0,
    `is_down_vote` TINYINT NOT NULL DEFAULT 0,
    `score`        TINYINT NOT NULL DEFAULT 0,
    `created_at`   TIMESTAMP(3)     DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
    `updated_at`   TIMESTAMP(3)     DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_COMMENT_ID` ON `comment_likes` (`comment_id`);
CREATE INDEX `IDX_PERSON_ID` ON `comment_likes` (`person_id`);

/**
  Comment Saves table
 */
CREATE TABLE `comment_saves`
(

    `id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
    `person_id`  BIGINT                                    NOT NULL,
    `comment_id` BIGINT                                    NOT NULL,
    `created_at` TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
    `updated_at` TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_COMMENT_SAVES_PERSON_ID` ON `comment_saves` (`person_id`);
CREATE INDEX `IDX_COMMENT_SAVES_COMMENT_ID` ON `comment_saves` (`comment_id`);

/**
  Communities table
 */
CREATE TABLE `communities`
(
    `id`                            BIGINT AUTO_INCREMENT PRIMARY KEY,
    `activity_pub_id`               TEXT         NOT NULL,
    `instance_id`                   BIGINT       NOT NULL,
    `title`                         VARCHAR(255) NOT NULL,
    `title_slug`                    VARCHAR(255) NOT NULL,
    `description`                   TEXT         NOT NULL,
    `is_deleted`                    TINYINT      NOT NULL DEFAULT 0,
    `is_removed`                    TINYINT      NOT NULL DEFAULT 0,
    `is_local`                      TINYINT      NOT NULL DEFAULT 0,
    `is_nsfw`                       TINYINT      NOT NULL DEFAULT 0,
    `is_posting_restricted_to_mods` TINYINT      NOT NULL DEFAULT 0,
    `icon_image_url`                TEXT         NOT NULL,
    `banner_image_url`              TEXT         NOT NULL,
    `public_key`                    TEXT         NOT NULL,
    `private_key`                   TEXT         NULL,
    `created_at`                    TIMESTAMP(3)          DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
    `updated_at`                    TIMESTAMP(3)          DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_COMMUNITIES_INSTANCE_ID` ON `communities` (`instance_id`);
CREATE INDEX `IDX_COMMUNITIES_TITLE_SLUG` ON `communities` (`title_slug`);
CREATE INDEX `IDX_COMMUNITIES_IS_DELETED` ON `communities` (`is_deleted`);
CREATE INDEX `IDX_COMMUNITIES_IS_REMOVED` ON `communities` (`is_removed`);
CREATE INDEX `IDX_COMMUNITIES_IS_LOCAL` ON `communities` (`is_local`);
CREATE INDEX `IDX_COMMUNITIES_IS_NSFW` ON `communities` (`is_nsfw`);
CREATE INDEX `IDX_COMMUNITIES_IS_POSTING_RESTRICTED_TO_MODS` ON `communities` (`is_posting_restricted_to_mods`);


/**
  Community Languages table
 */
CREATE TABLE `community_languages`
(
    `community_id` BIGINT NOT NULL,
    `language_id`  BIGINT NOT NULL,
    PRIMARY KEY (`community_id`, `language_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

/**
  Instances table
 */
CREATE TABLE `instances`
(
    `id`              BIGINT AUTO_INCREMENT PRIMARY KEY,
    `activity_pub_id` TEXT                                      NOT NULL,
    `domain`          VARCHAR(255)                              NOT NULL,
    `software`        VARCHAR(255)                              NOT NULL,
    `version`         VARCHAR(255)                              NOT NULL,
    `name`            VARCHAR(255)                              NOT NULL,
    `description`     TEXT                                      NOT NULL,
    `sidebar`         TEXT                                      NOT NULL,
    `icon_url`        TEXT                                      NOT NULL,
    `banner_url`      TEXT                                      NOT NULL,
    `public_key`      TEXT                                      NOT NULL,
    `private_key`     TEXT                                      NULL,
    `created_at`      TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
    `updated_at`      TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

/**
  Person Instance table
 */
CREATE TABLE `link_person_instances`
(
    `id`          BIGINT AUTO_INCREMENT PRIMARY KEY,
    `person_id`   BIGINT                                    NOT NULL,
    `instance_id` BIGINT                                    NOT NULL,
    `link_type`   ENUM ('super_admin', 'admin', 'user')     NOT NULL,
    `created_at`  TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE UNIQUE INDEX `IDX_LINK_PERSON_INSTANCE_PERSON_ID_INSTANCE_ID` ON `link_person_instances` (`person_id`, `instance_id`);


/**
  Instance Languages table
 */
CREATE TABLE `instance_languages`
(
    `instance_id` BIGINT NOT NULL,
    `language_id` BIGINT NOT NULL,
    PRIMARY KEY (`instance_id`, `language_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

/**
  Blocked Instances table
 */
CREATE TABLE `instance_blocks`
(
    `id`          BIGINT AUTO_INCREMENT PRIMARY KEY,
    `instance_id` BIGINT                                    NOT NULL,
    `created_at`  TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

/**
  Languages table
 */
CREATE TABLE `languages`
(
    `id`   BIGINT AUTO_INCREMENT PRIMARY KEY,
    `code` CHAR(3)      NOT NULL,
    `name` VARCHAR(255) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

/**
  People table
 */
CREATE TABLE `people`
(
    `id`                             BIGINT AUTO_INCREMENT PRIMARY KEY,
    `is_local`                       TINYINT      NOT NULL DEFAULT 0,
    `is_bot_account`                 TINYINT      NOT NULL DEFAULT 0,
    `is_banned`                      TINYINT      NOT NULL DEFAULT 0,
    `is_deleted`                     TINYINT      NOT NULL DEFAULT 0,
    `activity_pub_id`                TEXT         NOT NULL,
    `name`                           VARCHAR(255) NOT NULL,
    `display_name`                   VARCHAR(255) NOT NULL,
    `email`                          VARCHAR(255) NULL,
    `is_email_verified`              TINYINT      NOT NULL DEFAULT 0,
    `password`                       VARCHAR(255) NOT NULL,
    `avatar_image_url`               TEXT         NOT NULL,
    `banner_image_url`               TEXT         NOT NULL,
    `biography`                      TEXT         NOT NULL,
    `interface_language`             VARCHAR(20)  NULL,
    `default_theme`                  VARCHAR(255) NULL,
    `default_listing_type`           ENUM ('All', 'Local', 'Subscribed', 'ModeratorView'),
    `default_sort_type`              ENUM ('Active', 'Hot', 'New', 'Old', 'TopDay', 'TopWeek',
        'TopMonth', 'TopYear', 'TopAll', 'MostComments', 'NewComments', 'TopHour', 'TopSixHour',
        'TopTwelveHour', 'TopThreeMonths', 'TopSixMonths', 'TopNineMonths', 'Controversial', 'Scaled'),
    `is_show_scores`                 TINYINT      NOT NULL DEFAULT 0,
    `is_show_read_posts`             TINYINT      NOT NULL DEFAULT 0,
    `is_show_nsfw`                   TINYINT      NOT NULL DEFAULT 0,
    `is_show_new_post_notifications` TINYINT      NOT NULL DEFAULT 0,
    `is_show_bot_accounts`           TINYINT      NOT NULL DEFAULT 0,
    `is_show_avatars`                TINYINT      NOT NULL DEFAULT 0,
    `is_send_notifications_to_email` TINYINT      NOT NULL DEFAULT 0,
    `is_open_links_in_new_tab`       TINYINT      NOT NULL DEFAULT 0,
    `public_key`                     TEXT         NOT NULL,
    `private_key`                    TEXT         NULL,
    `created_at`                     TIMESTAMP(3)          DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
    `updated_at`                     TIMESTAMP(3)          DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)

) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_PEOPLE_NAME` ON `people` (`name`);
CREATE INDEX `IDX_PEOPLE_EMAIL` ON `people` (`email`);
CREATE INDEX `IDX_PEOPLE_IS_BANNED` ON `people` (`is_banned`);
CREATE INDEX `IDX_PEOPLE_IS_LOCAL` ON `people` (`is_local`);

CREATE TABLE `person_languages`
(
    `person_id`   BIGINT NOT NULL,
    `language_id` BIGINT NOT NULL,
    PRIMARY KEY (`person_id`, `language_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

/**
  Link Person Communities table
 */
CREATE TABLE `link_person_communities`
(
    `id`           BIGINT AUTO_INCREMENT PRIMARY KEY,
    `person_id`    BIGINT                                                               NOT NULL,
    `community_id` BIGINT                                                               NOT NULL,
    `link_type`    ENUM ('owner', 'moderator', 'follower', 'pending_follow', 'blocked') NOT NULL,
    `created_at`   TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)                            NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_LINK_PERSON_COMMUNITIES_PERSON_ID_COMMUNITY_ID` ON `link_person_communities` (`person_id`, `community_id`);
CREATE UNIQUE INDEX `IDX_LINK_PERSON_COMMUNITIES_PERSON_ID_COMMUNITY_ID_LINK_TYPE` ON `link_person_communities` (`person_id`, `community_id`, `link_type`);

/**
  Posts table
 */
CREATE TABLE `posts`
(
    `id`                       BIGINT AUTO_INCREMENT PRIMARY KEY,
    `instance_id`              BIGINT       NOT NULL,
    `activity_pub_id`          TEXT         NOT NULL,
    `language_id`              BIGINT       NOT NULL,
    `is_deleted`               TINYINT      NOT NULL DEFAULT 0,
    `is_removed`               TINYINT      NOT NULL DEFAULT 0,
    `is_local`                 TINYINT      NOT NULL DEFAULT 0,
    `community_id`             BIGINT       NOT NULL,
    `is_featured`              TINYINT      NOT NULL DEFAULT 0,
    `is_featured_in_community` TINYINT      NOT NULL DEFAULT 0,
    `link_url`                 TEXT         NULL,
    `link_title`               VARCHAR(255) NULL,
    `link_description`         TEXT         NULL,
    `link_thumbnail_url`       TEXT         NULL,
    `link_video_url`           TEXT         NULL,
    `is_nsfw`                  TINYINT      NOT NULL DEFAULT 0,
    `title`                    VARCHAR(255) NOT NULL,
    `title_slug`               VARCHAR(255) NOT NULL,
    `post_body`                TEXT,
    `public_key`               TEXT         NOT NULL,
    `private_key`              TEXT         NULL,
    `created_at`               TIMESTAMP(3)          DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
    `updated_at`               TIMESTAMP(3)          DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3),

    CONSTRAINT `UC_TITLE_SLUG` UNIQUE (`title_slug`)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_POSTS_LANGUAGE_ID` ON `posts` (`language_id`);
CREATE INDEX `IDX_POSTS_COMMUNITY_ID` ON `posts` (`community_id`);
CREATE INDEX `IDX_POSTS_IS_NSFW` ON `posts` (`is_nsfw`);
CREATE INDEX `IDX_POSTS_IS_FEATURED` ON `posts` (`is_featured`);
CREATE INDEX `IDX_POSTS_IS_FEATURED_IN_COMMUNITY` ON `posts` (`is_featured_in_community`);
CREATE INDEX `IDX_POSTS_TITLE_SLUG` ON `posts` (`title_slug`);

/**
  Post Likes table
 */
CREATE TABLE `post_likes`
(
    `id`           BIGINT AUTO_INCREMENT PRIMARY KEY,
    `post_id`      BIGINT  NOT NULL,
    `person_id`    BIGINT  NOT NULL,
    `is_up_vote`   TINYINT NOT NULL DEFAULT 0,
    `is_down_vote` TINYINT NOT NULL DEFAULT 0,
    `score`        TINYINT NOT NULL DEFAULT 0,
    `created_at`   TIMESTAMP(3)     DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
    `updated_at`   TIMESTAMP(3)     DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_POST_LIKES_POST_ID` ON `post_likes` (`post_id`);

/**
  Post Saves table
 */
CREATE TABLE `post_saves`
(

    `id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
    `person_id`  BIGINT                                    NOT NULL,
    `post_id`    BIGINT                                    NOT NULL,
    `created_at` TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
    `updated_at` TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_POST_SAVES_PERSON_ID` ON `post_saves` (`person_id`);
CREATE INDEX `IDX_POST_SAVES_POST_ID` ON `post_saves` (`post_id`);
CREATE UNIQUE INDEX `IDX_POST_SAVES_PERSON_ID_POST_ID` ON `post_saves` (`person_id`, `post_id`);

/**
  Person Posts table
 */
CREATE TABLE `link_person_posts`
(
    `id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
    `person_id`  BIGINT                                         NOT NULL,
    `post_id`    BIGINT                                         NOT NULL,
    `link_type`  ENUM ('creator', 'follower', 'pending_follow') NOT NULL,
    `created_at` TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)      NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

/**
  Person Access Control List table
 */
CREATE TABLE `acl`
(
    `id`                BIGINT AUTO_INCREMENT PRIMARY KEY,
    `person_id`         BIGINT           NOT NULL,
    `entity_type`       ENUM ('community', 'post', 'comment',
        'report', 'message', 'instance') NOT NULL,
    `entity_id`         BIGINT           NULL     DEFAULT NULL,
    `authorized_action` ENUM ('create', 'read', 'update',
        'delete', 'post', 'comment', 'message',
        'ban', 'purge', 'follow')        NOT NULL,
    `is_permitted`      TINYINT          NOT NULL DEFAULT 0,
    `created_at`        TIMESTAMP(3)              DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
    `updated_at`        TIMESTAMP(3)              DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_ACL_PERSON_ID_ENTITY_TYPE_ENTITY_ID` ON `acl` (`person_id`, `entity_type`, `entity_id`);
CREATE UNIQUE INDEX `IDX_ACL_PERSON_ID_ENTITY_TYPE_ENTITY_ID_AUTHORIZED_ACTION` ON `acl` (`person_id`, `entity_type`, `entity_id`, `authorized_action`);

/**
  Post Read table
 */
CREATE TABLE `post_reads`
(
    `id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
    `post_id`    BIGINT                                    NOT NULL,
    `person_id`  BIGINT                                    NOT NULL,
    `created_at` TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE UNIQUE INDEX `IDX_POST_READ_POST_ID_PERSON_ID` ON `post_reads` (`post_id`, `person_id`);