/**
  Add moderation_logs columns
 */
ALTER TABLE `moderation_logs`
    ADD COLUMN comment_id BIGINT NULL;
ALTER TABLE `moderation_logs`
  ADD COLUMN featured_community TINYINT NULL;

/**
  Drop unique index because some mod action can be undone such as unban
 */
DROP INDEX `IDX_MODERATION_LOGS_INSTANCE_ID_ACTION_TYPE_ENTITY_ID` ON `moderation_logs`;