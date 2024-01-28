/**
 * Comments table
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
  `comment_body`    TEXT    NULL,
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
  `title`                         VARCHAR(255) NULL,
  `title_slug`                    VARCHAR(255) NOT NULL,
  `description`                   TEXT         NULL,
  `is_deleted`                    TINYINT      NOT NULL DEFAULT 0,
  `is_removed`                    TINYINT      NOT NULL DEFAULT 0,
  `is_local`                      TINYINT      NOT NULL DEFAULT 0,
  `is_nsfw`                       TINYINT      NOT NULL DEFAULT 0,
  `is_posting_restricted_to_mods` TINYINT      NOT NULL DEFAULT 0,
  `icon_image_url`                TEXT         NULL,
  `banner_image_url`              TEXT         NULL,
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
  `software`        VARCHAR(255)                              NULL,
  `version`         VARCHAR(255)                              NULL,
  `name`            VARCHAR(255)                              NULL,
  `description`     TEXT                                      NULL,
  `sidebar`         TEXT                                      NULL,
  `icon_url`        TEXT                                      NULL,
  `banner_url`      TEXT                                      NULL,
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
  `is_local`                       TINYINT                            NOT NULL DEFAULT 0,
  `is_bot_account`                 TINYINT                            NOT NULL DEFAULT 0,
  `is_deleted`                     TINYINT                            NOT NULL DEFAULT 0,
  `activity_pub_id`                TEXT                               NOT NULL,
  `role_id`                        BIGINT                             NOT NULL,
  `name`                           VARCHAR(255)                       NULL,
  `display_name`                   VARCHAR(255)                       NULL,
  `email`                          VARCHAR(255)                       NULL,
  `is_email_verified`              TINYINT                            NOT NULL DEFAULT 0,
  `password`                       VARCHAR(255)                       NOT NULL,
  `avatar_image_url`               TEXT                               NULL,
  `banner_image_url`               TEXT                               NULL,
  `biography`                      TEXT                               NULL,
  `interface_language`             VARCHAR(20)                        NULL,
  `default_theme`                  VARCHAR(255)                       NULL,
  `default_listing_type`           ENUM ('All','Local','Subscribed','ModeratorView'),
  `default_sort_type`              ENUM ('Active','Hot','New','Old','TopDay','TopWeek',
    'TopMonth','TopYear','TopAll','MostComments','NewComments','TopHour','TopSixHour',
    'TopTwelveHour','TopThreeMonths','TopSixMonths','TopNineMonths','Controversial','Scaled'),
  `is_show_scores`                 TINYINT                            NOT NULL DEFAULT 0,
  `is_show_read_posts`             TINYINT                            NOT NULL DEFAULT 0,
  `is_show_nsfw`                   TINYINT                            NOT NULL DEFAULT 0,
  `is_show_bot_accounts`           TINYINT                            NOT NULL DEFAULT 0,
  `is_show_avatars`                TINYINT                            NOT NULL DEFAULT 0,
  `is_send_notifications_to_email` TINYINT                            NOT NULL DEFAULT 0,
  `is_open_links_in_new_tab`       TINYINT                            NOT NULL DEFAULT 0,
  `is_infinite_scroll`             TINYINT                            NOT NULL DEFAULT 0,
  `is_keyboard_navigation`         TINYINT                            NOT NULL DEFAULT 0,
  `is_animated_images`             TINYINT                            NOT NULL DEFAULT 0,
  `is_collapse_bot_comments`       TINYINT                            NOT NULL DEFAULT 0,
  `is_auto_expanding`              TINYINT                            NOT NULL DEFAULT 0,
  `is_blur_nsfw`                   TINYINT                            NOT NULL DEFAULT 0,
  `post_listing_type`              ENUM ('List', 'Card', 'SmallCard') NOT NULL DEFAULT 'List',
  `matrix_user_id`                 TEXT                               NULL,
  `public_key`                     TEXT                               NOT NULL,
  `private_key`                    TEXT                               NULL,
  `totp_secret`                    TEXT                               NULL,
  `totp_verified_secret`           TEXT                               NULL,
  `created_at`                     TIMESTAMP(3)                                DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
  `updated_at`                     TIMESTAMP(3)                                DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_PEOPLE_NAME` ON `people` (`name`);
CREATE INDEX `IDX_PEOPLE_EMAIL` ON `people` (`email`);
CREATE INDEX `IDX_PEOPLE_IS_LOCAL` ON `people` (`is_local`);
CREATE INDEX `IDX_PEOPLE_ROLE_ID` ON `people` (`role_id`);

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
  `person_id`    BIGINT                                                                    NOT NULL,
  `community_id` BIGINT                                                                    NOT NULL,
  `link_type`    ENUM ('owner','moderator','follower','pending_follow','blocked','banned') NOT NULL,
  `created_at`   TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)                                 NOT NULL
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
  `is_locked`                TINYINT      NOT NULL DEFAULT 0,
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
  `post_body`                TEXT         NULL,
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
  `person_id`  BIGINT                                       NOT NULL,
  `post_id`    BIGINT                                       NOT NULL,
  `link_type`  ENUM ('creator','follower','pending_follow') NOT NULL,
  `created_at` TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)    NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

/**
 Person Access Control List table
 */
CREATE TABLE `acl`
(
  `id`                BIGINT AUTO_INCREMENT PRIMARY KEY,
  `person_id`         BIGINT       NOT NULL,
  `entity_type`       ENUM ('community','post','comment',
    'report','message','instance') NOT NULL,
  `entity_id`         BIGINT       NULL     DEFAULT NULL,
  `authorized_action` ENUM ('create','read','update',
    'delete','post','comment','message',
    'ban','purge','follow')        NOT NULL,
  `is_permitted`      TINYINT      NOT NULL DEFAULT 0,
  `created_at`        TIMESTAMP(3)          DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
  `updated_at`        TIMESTAMP(3)          DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_ACL_PERSON_ID_ENTITY_TYPE_ENTITY_ID` ON `acl` (`person_id`, `entity_type`, `entity_id`);
CREATE UNIQUE INDEX `IDX_ACL_PERSON_ID_ENTITY_TYPE_ENTITY_ID_AUTHORIZED_ACTION` ON `acl` (`person_id`,
                                                                                          `entity_type`,
                                                                                          `entity_id`,
                                                                                          `authorized_action`);

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

/**
 Comment Read table
 */
CREATE TABLE `comment_reads`
(
  `id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
  `comment_id` BIGINT                                    NOT NULL,
  `person_id`  BIGINT                                    NOT NULL,
  `created_at` TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE UNIQUE INDEX `IDX_COMMENT_READ_COMMENT_ID_PERSON_ID` ON `comment_reads` (`comment_id`, `person_id`);

/**
 Moderation Logs table
 */
CREATE TABLE `moderation_logs`
(
  `id`                   BIGINT AUTO_INCREMENT PRIMARY KEY,
  `instance_id`          BIGINT                                    NOT NULL,
  `action_type`          VARCHAR(255)                              NOT NULL,
  `reason`               VARCHAR(255)                              NULL,
  `entity_id`            BIGINT                                    NOT NULL,
  `admin_person_id`      BIGINT                                    NULL,
  `post_id`              BIGINT                                    NULL,
  `comment_id`           BIGINT                                    NULL,
  `community_id`         BIGINT                                    NULL,
  `moderation_person_id` BIGINT                                    NULL,
  `other_person_id`      BIGINT                                    NULL,
  `removed`              TINYINT                                   NULL,
  `hidden`               TINYINT                                   NULL,
  `locked`               TINYINT                                   NULL,
  `banned`               TINYINT                                   NULL,
  `featured`             TINYINT                                   NULL,
  `featured_community`   TINYINT                                   NULL,
  `expires`              TIMESTAMP(3)                              NULL,
  `created_at`           TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_MODERATION_LOGS_ACTION_TYPE` ON `moderation_logs` (`action_type`);

/**
 Post Post Cross Post table
 */
CREATE TABLE `post_post_cross_post`
(
  `post_id`       BIGINT NOT NULL,
  `cross_post_id` BIGINT NOT NULL,
  PRIMARY KEY (`post_id`, `cross_post_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

/**
 Post Cross Posts table
 */
CREATE TABLE `post_cross_posts`
(
  `id`       BIGINT AUTO_INCREMENT PRIMARY KEY,
  `md5_hash` CHAR(32) CHARACTER SET 'latin1' NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_POST_CROSS_POST_MD5_HASH` ON `post_cross_posts` (`md5_hash`);

/**

 Post Mention table
 */
CREATE TABLE `people_mentions`
(
  `id`           BIGINT AUTO_INCREMENT PRIMARY KEY,
  `recipient_id` BIGINT                                    NOT NULL,
  `comment_id`   BIGINT                                    NOT NULL,
  `is_read`      TINYINT      DEFAULT 0                    NOT NULL,
  `created_at`   TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE UNIQUE INDEX `IDX_PEOPLE_MENTIONS_RECIPIENT_ID` ON `people_mentions` (`recipient_id`);

/**
 Private Message
 */
CREATE TABLE `private_messages`
(
  `id`              BIGINT AUTO_INCREMENT PRIMARY KEY,
  `sender_id`       BIGINT                                    NOT NULL,
  `recipient_id`    BIGINT                                    NOT NULL,
  `activity_pub_id` TEXT                                      NOT NULL,
  `content`         TEXT                                      NOT NULL,
  `is_local`        TINYINT      DEFAULT 0                    NOT NULL,
  `is_deleted`      TINYINT      DEFAULT 0                    NOT NULL,
  `is_read`         TINYINT      DEFAULT 0                    NOT NULL,
  `created_at`      TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
  `updated_at`      TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_PRIVATE_MESSAGES_SENDER_ID` ON `private_messages` (`sender_id`);
CREATE INDEX `IDX_PRIVATE_MESSAGES_RECIPIENT_ID` ON `private_messages` (`recipient_id`);

/**
 Private Message Report
 */
CREATE TABLE `private_messages_reports`
(
  `id`                 BIGINT AUTO_INCREMENT PRIMARY KEY,
  `creator_id`         BIGINT                                    NOT NULL,
  `resolver_id`        BIGINT                                    NULL,
  `private_message_id` BIGINT                                    NOT NULL,
  `original_content`   TEXT                                      NOT NULL,
  `reason`             TEXT                                      NOT NULL,
  `resolved`           TINYINT      DEFAULT 0                    NOT NULL,
  `created_at`         TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
  `updated_at`         TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_PRIVATE_MESSAGES_REPORT_CREATOR_ID` ON `private_messages_reports` (`creator_id`);
CREATE INDEX `IDX_PRIVATE_MESSAGES_REPORT_PRIVATE_MESSAGE_ID` ON `private_messages_reports` (`private_message_id`);

/**
 Comment Report
 */
CREATE TABLE `comment_reports`
(
  `id`               BIGINT AUTO_INCREMENT PRIMARY KEY,
  `creator_id`       BIGINT                                    NOT NULL,
  `resolver_id`      BIGINT                                    NULL,
  `comment_id`       BIGINT                                    NOT NULL,
  `original_content` TEXT                                      NOT NULL,
  `reason`           TEXT                                      NOT NULL,
  `resolved`         TINYINT      DEFAULT 0                    NOT NULL,
  `created_at`       TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
  `updated_at`       TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_COMMENT_REPORTS_CREATOR_ID` ON `comment_reports` (`creator_id`);

/**
 Comment Reply
 */
CREATE TABLE `comment_replies`
(
  `id`           BIGINT AUTO_INCREMENT PRIMARY KEY,
  `recipient_id` BIGINT                                    NOT NULL,
  `comment_id`   BIGINT                                    NOT NULL,
  `is_read`      TINYINT      DEFAULT 0                    NOT NULL,
  `created_at`   TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
  `updated_at`   TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';


CREATE INDEX `IDX_COMMENT_REPLIES_RECIPIENT_ID` ON `comment_replies` (`recipient_id`);

/**
    Post Report
    */
CREATE TABLE `post_reports`
(
  `id`             BIGINT AUTO_INCREMENT PRIMARY KEY,
  `creator_id`     BIGINT                                    NOT NULL,
  `resolver_id`    BIGINT                                    NULL,
  `post_id`        BIGINT                                    NOT NULL,
  `original_title` TEXT                                      NOT NULL,
  `original_body`  TEXT                                      NOT NULL,
  `original_url`   TEXT                                      NOT NULL,
  `reason`         TEXT                                      NOT NULL,
  `resolved`       TINYINT      DEFAULT 0                    NOT NULL,
  `created_at`     TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
  `updated_at`     TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_POST_REPORTS_CREATOR_ID` ON `post_reports` (`creator_id`);


/**
    Person Registration Application
    */
CREATE TABLE `person_applications`
(
  `id`                 BIGINT AUTO_INCREMENT PRIMARY KEY,
  `admin_id`           BIGINT                                    NULL,
  `person_id`          BIGINT                                    NOT NULL,
  `question`           TEXT                                      NULL,
  `answer`             TEXT                                      NULL,
  `application_status` ENUM ('pending','approved','rejected')    NOT NULL,
  `created_at`         TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
  `updated_at`         TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_PEOPLE_APPLICATIONS_PERSON_ID` ON `person_applications` (`person_id`);

/**
    Instance Config
    */

CREATE TABLE `instance_configs`
(
  `id`                            BIGINT AUTO_INCREMENT PRIMARY KEY,
  `instance_id`                   BIGINT                                               NOT NULL UNIQUE,
  `registration_mode`             ENUM ('Closed', 'RequireApplication', 'Open')        NOT NULL DEFAULT 'Closed',
  `registration_question`         TEXT                                                 NULL,
  `private_instance`              TINYINT                                              NULL,
  `enable_downvotes`              TINYINT                                              NULL,
  `enable_nsfw`                   TINYINT                                              NULL,
  `community_creation_admin_only` TINYINT                                              NULL,
  `application_email_admins`      TINYINT                                              NULL,
  `hide_modlog_mod_names`         TINYINT                                              NULL,
  `federation_enabled`            TINYINT                                              NULL,
  `captcha_enabled`               TINYINT                                              NULL,
  `captcha_difficulty`            TEXT                                                 NULL,
  `require_email_verification`    TINYINT                                              NULL,
  `actor_name_max_length`         BIGINT                                               NULL,
  `default_theme`                 TEXT                                                 NULL,
  `legal_information`             TEXT                                                 NULL,
  `default_post_listing_type`     ENUM ('All', 'Local', 'Subscribed', 'ModeratorView') NULL,
  `created_at`                    timestamp(3)                                         NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `updated_at`                    timestamp(3)                                         NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

/**
  Slur Filters
 */
CREATE TABLE `slur_filters`
(
  `id`               BIGINT AUTO_INCREMENT PRIMARY KEY,
  `slur_action_type` ENUM ('BLOCK','REMOVE','REPLACE')         NOT NULL,
  `slur_regex`       TEXT                                      NOT NULL,
  `created_at`       TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
  `updated_at`       TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

/**
  Announcements
 */
CREATE TABLE `announcements`
(
  `id`            BIGINT AUTO_INCREMENT PRIMARY KEY,
  `content`       TEXT                                      NOT NULL,
  `local_site_id` BIGINT                                    NOT NULL,
  `created_at`    TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
  `updated_at`    TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

/*
  Captcha
 */
CREATE TABLE `captcha`
(
  `id`            BIGINT AUTO_INCREMENT PRIMARY KEY,
  `uuid`          VARCHAR(36)                               NOT NULL,
  `word`          TEXT                                      NOT NULL,
  `png`           TEXT                                      NOT NULL,
  `wav`           TEXT                                      NULL,
  `locked`        BOOLEAN DEFAULT false                     NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

/*
  Custom Emoji table
 */
CREATE TABLE `custom_emojis`
(
  `id`            BIGINT AUTO_INCREMENT PRIMARY KEY,
  `local_site_id` BIGINT                                    NOT NULL,
  `alt_text`      TEXT                                      NOT NULL,
  `image_url`     TEXT                                      NOT NULL,
  `category`      TEXT                                      NOT NULL,
  `shortcode`     VARCHAR(128)                              NOT NULL,
  `created_at`    TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
  `updated_at`    TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

/**
  Custom Emoji Keyword table
 */
CREATE TABLE `custom_emoji_keywords`
(
  `id`              BIGINT AUTO_INCREMENT PRIMARY KEY,
  `custom_emoji_id` BIGINT       NOT NULL,
  `keyword`         VARCHAR(255) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_CUSTOM_EMOJI_KEYWORD_CUSTOM_EMOJI_ID` ON `custom_emoji_keywords` (`custom_emoji_id`);


/**
  Roles table
 */
CREATE TABLE `roles`
(
  `id`          BIGINT AUTO_INCREMENT PRIMARY KEY,
  `name`        VARCHAR(255)                              NOT NULL,
  `description` TEXT                                      NOT NULL,
  `is_active`   TINYINT                                   NOT NULL DEFAULT 1,
  `expires_at`  TIMESTAMP(3)                              NULL,
  `created_at`  TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
  `updated_at`  TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

/**
  Rolepermissions table
 */
CREATE TABLE `role_permissions`
(
  `id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
  `role_id`    BIGINT NOT NULL,
  `permission` TEXT   NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_ROLE_PERMISSIONS_ROLE_ID` ON `role_permissions` (`role_id`);
CREATE UNIQUE INDEX `IDX_ROLE_PERMISSIONS_ROLE_ID_PERMISSION` ON `role_permissions` (role_id, permission(255));
