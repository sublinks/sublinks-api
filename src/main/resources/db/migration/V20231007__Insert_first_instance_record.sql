INSERT INTO `instances`
(`activity_pub_id`, `domain`, `software`, `version`, `name`, `description`, `sidebar`, `icon_url`, `banner_url`,
 `public_key`, `private_key`)
VALUES ("", "", "", "", "", "", "", "", "", "", "");

INSERT INTO `instance_aggregates`
(`instance_id`, `user_count`, `post_count`, `comment_count`, `community_count`, `active_daily_user_count`,
 `active_weekly_user_count`,
 `active_monthly_user_count`, `active_half_year_user_count`)
VALUES (1, 0, 0, 0, 0, 0, 0, 0, 0);