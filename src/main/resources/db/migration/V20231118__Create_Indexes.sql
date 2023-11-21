/**
  Posts table
 */
ALTER TABLE `posts`
    ADD FULLTEXT (title, post_body);

/**
  Comments table
 */
ALTER TABLE `comments`
    ADD FULLTEXT (comment_body);

/**
  People table
 */
ALTER TABLE `people`
    ADD FULLTEXT (name, display_name);

/**
  Community table
 */
ALTER TABLE `communities`
    ADD FULLTEXT (title, title_slug, description);
