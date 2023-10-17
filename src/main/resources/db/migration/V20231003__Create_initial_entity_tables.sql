/**
  Comments table
 */
create table comments
(
    `id`              bigint auto_increment primary key,
    `activity_pub_id` text    not null,
    `language_id`     bigint  not null,
    `is_deleted`      tinyint not null default 0,
    `is_removed`      tinyint not null default 0,
    `person_id`       bigint  not null,
    `community_id`    bigint  not null,
    `post_id`         bigint  not null,
    `is_featured`     tinyint not null default 0,
    `comment_body`    text,
    `path`            varchar(512),
    `created_at`      timestamp(3)     default current_timestamp(3) not null,
    `updated_at`      timestamp(3)     default current_timestamp(3) not null on update current_timestamp (3)
) engine = InnoDB
  default charset `utf8mb4`
  collate = 'utf8mb4_unicode_ci';

create index `IDX_COMMENTS_LANGUAGE_ID` on `comments` (`language_id`);
create index `IDX_COMMENTS_PERSON_ID` on `comments` (`person_id`);
create index `IDX_COMMENTS_COMMUNITY_ID` on `comments` (`community_id`);
create index `IDX_COMMENTS_POST_ID` on `comments` (`post_id`);

/**
  Comment Likes table
 */
create table comment_likes
(
    `id`              bigint auto_increment primary key,
    `activity_pub_id` text    not null,
    `person_id`       bigint  not null,
    `comment_id`      bigint  not null,
    `is_up_vote`      tinyint not null default 0,
    `is_down_vote`    tinyint not null default 0,
    `created_at`      timestamp(3)     default current_timestamp(3) not null,
    `updated_at`      timestamp(3)     default current_timestamp(3) not null on update current_timestamp (3)
) engine = InnoDB
  default charset `utf8mb4`
  collate = 'utf8mb4_unicode_ci';

create index `IDX_COMMENT_ID` on `comment_likes` (`comment_id`);
create index `IDX_PERSON_ID` on `comment_likes` (`person_id`);

/**
  Communities table
 */
create table communities
(
    `id`                            bigint auto_increment primary key,
    `activity_pub_id`               text         not null,
    `instance_id`                   bigint       not null,
    `title`                         varchar(255) not null,
    `title_slug`                    varchar(255) not null,
    `description`                   text         not null,
    `is_deleted`                    tinyint      not null default 0,
    `is_removed`                    tinyint      not null default 0,
    `is_local`                      tinyint      not null default 0,
    `is_nsfw`                       tinyint      not null default 0,
    `is_posting_restricted_to_mods` tinyint      not null default 0,
    `icon_image_url`                text         not null,
    `banner_image_url`              text         not null,
    `public_key`                    text         not null,
    `private_key`                   text null,
    `created_at`                    timestamp(3)          default current_timestamp(3) not null,
    `updated_at`                    timestamp(3)          default current_timestamp(3) not null on update current_timestamp (3)
) engine = InnoDB
  default charset `utf8mb4`
  collate = 'utf8mb4_unicode_ci';

create index `IDX_COMMUNITIES_INSTANCE_ID` on `communities` (`instance_id`);
create index `IDX_COMMUNITIES_TITLE_SLUG` on `communities` (`title_slug`);
create index `IDX_COMMUNITIES_IS_DELETED` on `communities` (`is_deleted`);
create index `IDX_COMMUNITIES_IS_REMOVED` on `communities` (`is_removed`);
create index `IDX_COMMUNITIES_IS_LOCAL` on `communities` (`is_local`);
create index `IDX_COMMUNITIES_IS_NSFW` on `communities` (`is_nsfw`);
create index `IDX_COMMUNITIES_IS_POSTING_RESTRICTED_TO_MODS` on `communities` (`is_posting_restricted_to_mods`);

/**
  Instances table
 */
create table instances
(
    `id`              bigint auto_increment primary key,
    `activity_pub_id` text                                      not null,
    `domain`          varchar(255)                              not null,
    `software`        varchar(255)                              not null,
    `version`         varchar(255)                              not null,
    `name`            varchar(255)                              not null,
    `description`     text                                      not null,
    `sidebar`         text                                      not null,
    `icon_url`        text                                      not null,
    `banner_url`      text                                      not null,
    `public_key`      text                                      not null,
    `private_key`     text null,
    `created_at`      timestamp(3) default current_timestamp(3) not null,
    `updated_at`      timestamp(3) default current_timestamp(3) not null on update current_timestamp (3)
) engine = InnoDB
  default charset `utf8mb4`
  collate = 'utf8mb4_unicode_ci';

/**
  Languages table
 */
create table languages
(
    `id`   bigint auto_increment primary key,
    `code` char(3)      not null,
    `name` varchar(255) not null
) engine = InnoDB
  default charset `utf8mb4`
  collate = 'utf8mb4_unicode_ci';

/**
  People table
 */
create table people
(
    `id`                             bigint auto_increment primary key,
    `instance_id`                    bigint       not null,
    `is_local`                       tinyint      not null default 0,
    `is_bot_account`                 tinyint      not null default 0,
    `is_banned`                      tinyint      not null default 0,
    `is_deleted`                     tinyint      not null default 0,
    `activity_pub_id`                text         not null,
    `name`                           varchar(255) not null,
    `display_name`                   varchar(255) not null,
    `email`                          varchar(255) null,
    `is_email_verified`              tinyint      not null default 0,
    `password`                       varchar(255) not null,
    `avatar_image_url`               text         not null,
    `banner_image_url`               text         not null,
    `biography`                      text         not null,
    `interface_language`             varchar(20) null,
    `default_theme`                  varchar(255) null,
    `default_listing_type`           enum ('All', 'Local', 'Subscribed', 'ModeratorView'),
    `default_sort_type`              enum ('Active', 'Hot', 'New', 'Old', 'TopDay', 'TopWeek',
        'TopMonth', 'TopYear', 'TopAll', 'MostComments', 'NewComments', 'TopHour', 'TopSixHour',
        'TopTwelveHour', 'TopThreeMonths', 'TopSixMonths', 'TopNineMonths', 'Controversial', 'Scaled'),
    `is_show_scores`                 tinyint      not null default 0,
    `is_show_read_posts`             tinyint      not null default 0,
    `is_show_nsfw`                   tinyint      not null default 0,
    `is_show_new_post_notifications` tinyint      not null default 0,
    `is_show_bot_accounts`           tinyint      not null default 0,
    `is_show_avatars`                tinyint      not null default 0,
    `is_send_notifications_to_email` tinyint      not null default 0,
    `is_open_links_in_new_tab`       tinyint      not null default 0,
    `public_key`                     text         not null,
    `private_key`                    text null,
    `created_at`                     timestamp(3)          default current_timestamp(3) not null,
    `updated_at`                     timestamp(3)          default current_timestamp(3) not null on update current_timestamp (3)

) engine = InnoDB
    default charset `utf8mb4`
    collate = 'utf8mb4_unicode_ci';

create index `IDX_PEOPLE_NAME` on `people` (`name`);
create index `IDX_PEOPLE_EMAIL` on `people` (`email`);
create index `IDX_PEOPLE_IS_BANNED` on `people` (`is_banned`);
create index `IDX_PEOPLE_IS_LOCAL` on `people` (`is_local`);

/**
  Person Communities table
 */
create table link_person_communities
(
    `id`           bigint auto_increment primary key,
    `person_id`    bigint                                    not null,
    `community_id` bigint                                    not null,
    `created_at`   timestamp(3) default current_timestamp(3) not null,
) engine = InnoDB
  default charset `utf8mb4`
  collate = 'utf8mb4_unicode_ci';

/**
  Posts table
 */
create table posts
(
    `id`                       bigint auto_increment primary key,
    `instance_id`              bigint       not null,
    `activity_pub_id`          text         not null,
    `language_id`              bigint       not null,
    `is_deleted`               tinyint      not null default 0,
    `is_removed`               tinyint      not null default 0,
    `community_id`             bigint       not null,
    `is_featured`              tinyint      not null default 0,
    `is_featured_in_community` tinyint      not null default 0,
    `link_url`                 text null,
    `link_title`               varchar(255) null,
    `link_description`         text null,
    `link_thumbnail_url`       text null,
    `link_video_url`           text null,
    `is_nsfw`                  tinyint      not null default 0,
    `title`                    varchar(255) not null,
    `title_slug`               varchar(255) not null,
    `post_body`                text,
    `public_key`               text         not null,
    `private_key`              text null,
    `created_at`               timestamp(3)          default current_timestamp(3) not null,
    `updated_at`               timestamp(3)          default current_timestamp(3) not null on update current_timestamp (3),

    constraint `UC_TITLE_SLUG` unique (`title_slug`)
) engine = InnoDB
  default charset `utf8mb4`
  collate = 'utf8mb4_unicode_ci';

create index `IDX_POSTS_LANGUAGE_ID` on `posts` (`language_id`);
create index `IDX_POSTS_COMMUNITY_ID` on `posts` (`community_id`);
create index `IDX_POSTS_IS_NSFW` on `posts` (`is_nsfw`);
create index `IDX_POSTS_IS_FEATURED` on `posts` (`is_featured`);
create index `IDX_POSTS_IS_FEATURED_IN_COMMUNITY` on `posts` (`is_featured_in_community`);
create index `IDX_POSTS_TITLE_SLUG` on `posts` (`title_slug`);

/**
  Post Likes table
 */
create table post_likes
(
    `id`           bigint auto_increment primary key,
    `post_id`      bigint  not null,
    `is_up_vote`   tinyint not null default 0,
    `is_down_vote` tinyint not null default 0,
    `created_at`   timestamp(3)     default current_timestamp(3) not null,
    `updated_at`   timestamp(3)     default current_timestamp(3) not null on update current_timestamp (3)
) engine = InnoDB
  default charset `utf8mb4`
  collate = 'utf8mb4_unicode_ci';

create index `IDX_POST_LIKES_POST_ID` on `post_likes` (`post_id`);

/**
  Person Posts table
 */
create table link_person_posts
(
    `id`         bigint auto_increment primary key,
    `person_id`  bigint                                    not null,
    `post_id`    bigint                                    not null,
    `created_at` timestamp(3) default current_timestamp(3) not null,
) engine = InnoDB
  default charset `utf8mb4`
  collate = 'utf8mb4_unicode_ci';

/**
  Person Access Control List table
 */
create table link_person_acl
(
    `id`          bigint auto_increment primary key,
    `person_id`   bigint  not null,
    `entity_type` enum("community", "post", "comment", "report", "message", "instance") not null,
    `entity_id`   bigint null default null,
    `can_create`  tinyint not null default 0,
    `can_read`    tinyint not null default 0,
    `can_update`  tinyint not null default 0,
    `can_delete`  tinyint not null default 0,
    `created_at`  timestamp(3)     default current_timestamp(3) not null,
    `updated_at`  timestamp(3)     default current_timestamp(3) not null on update current_timestamp (3)
) engine = InnoDB
  default charset `utf8mb4`
  collate = 'utf8mb4_unicode_ci';