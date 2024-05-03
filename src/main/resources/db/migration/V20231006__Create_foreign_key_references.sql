/**
  Media
 */
ALTER TABLE media
    ADD FOREIGN KEY (person_id) REFERENCES people (id) ON DELETE CASCADE;

/**
  Comments table
 */
ALTER TABLE `comments`
    ADD FOREIGN KEY (`language_id`) REFERENCES `languages` (`id`) ON DELETE CASCADE,
    ADD FOREIGN KEY (`person_id`) REFERENCES `people` (`id`) ON DELETE CASCADE,
    ADD FOREIGN KEY (`community_id`) REFERENCES `communities` (`id`) ON DELETE CASCADE,
    ADD FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE;

/**
  Comment Aggregates table
 */
ALTER TABLE `comment_aggregates`
    ADD FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`) ON DELETE CASCADE;

/**
  Comment Likes table
 */
ALTER TABLE `comment_likes`
    ADD FOREIGN KEY (`person_id`) REFERENCES `people` (`id`) ON DELETE CASCADE,
    ADD FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`) ON DELETE CASCADE;

/**
  Communities table
 */
ALTER TABLE `communities`
    ADD FOREIGN KEY (`instance_id`) REFERENCES `instances` (`id`) ON DELETE CASCADE;

/**
  Community Languages table
 */
ALTER TABLE `community_languages`
    ADD FOREIGN KEY (`community_id`) REFERENCES `communities` (`id`) ON DELETE CASCADE,
    ADD FOREIGN KEY (`language_id`) REFERENCES `languages` (`id`) ON DELETE CASCADE;

/**
  Community Aggregates table
 */
ALTER TABLE `community_aggregates`
    ADD FOREIGN KEY (`community_id`) REFERENCES `communities` (`id`) ON DELETE CASCADE;

/**
  Instance Aggregates table
 */
ALTER TABLE `instance_aggregates`
    ADD FOREIGN KEY (`instance_id`) REFERENCES `instances` (`id`) ON DELETE CASCADE;

/**
  Instance Languages table
 */
ALTER TABLE `instance_languages`
    ADD FOREIGN KEY (`instance_id`) REFERENCES `instances` (`id`) ON DELETE CASCADE,
    ADD FOREIGN KEY (`language_id`) REFERENCES `languages` (`id`) ON DELETE CASCADE;

/**
  Person Aggregates table
 */
ALTER TABLE `person_aggregates`
    ADD FOREIGN KEY (`person_id`) REFERENCES `people` (`id`) ON DELETE CASCADE;

/**
  Link Person Communities table
 */
ALTER TABLE `link_person_instances`
    ADD FOREIGN KEY (`person_id`) REFERENCES `people` (`id`) ON DELETE CASCADE,
    ADD FOREIGN KEY (`instance_id`) REFERENCES `instances` (`id`) ON DELETE CASCADE;

/**
  Person Languages table
 */
ALTER TABLE `person_languages`
    ADD FOREIGN KEY (`person_id`) REFERENCES `people` (`id`) ON DELETE CASCADE,
    ADD FOREIGN KEY (`language_id`) REFERENCES `languages` (`id`) ON DELETE CASCADE;

/**
  Posts table
 */
ALTER TABLE `posts`
    ADD FOREIGN KEY (`instance_id`) REFERENCES `instances` (`id`) ON DELETE CASCADE,
    ADD FOREIGN KEY (`language_id`) REFERENCES `languages` (`id`) ON DELETE CASCADE,
    ADD FOREIGN KEY (`community_id`) REFERENCES `communities` (`id`) ON DELETE CASCADE;

/**
  Post Aggregates table
 */
ALTER TABLE `post_aggregates`
    ADD FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE,
    ADD FOREIGN KEY (`community_id`) REFERENCES `communities` (`id`) ON DELETE CASCADE;

/**
  Posts Likes table
 */
ALTER TABLE `post_likes`
    ADD FOREIGN KEY (`person_id`) REFERENCES `people` (`id`) ON DELETE CASCADE,
    ADD FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE;

/**
  Comment Likes table
 */
ALTER TABLE `comment_likes`
    ADD FOREIGN KEY (`person_id`) REFERENCES `people` (`id`) ON DELETE CASCADE,
    ADD FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`) ON DELETE CASCADE;

/**
  Post Post Cross Post table
 */
ALTER TABLE `post_post_cross_post`
    ADD FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE,
    ADD FOREIGN KEY (`cross_post_id`) REFERENCES `post_cross_posts` (`id`) ON DELETE CASCADE;

/**
  Private Messages table
 */
ALTER TABLE `private_messages`
    ADD FOREIGN KEY (`sender_id`) REFERENCES `people` (`id`) ON DELETE CASCADE,
    ADD FOREIGN KEY (`recipient_id`) REFERENCES `people` (`id`) ON DELETE CASCADE;