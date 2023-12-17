/**
  Add moderation_logs columns
 */
ALTER TABLE `moderation_logs`
  ADD COLUMN comment_id BIGINT NULL AFTER post_id;
ALTER TABLE `moderation_logs`
  ADD COLUMN featured_community TINYINT NULL AFTER featured;

--  Drop unique index because some mod action can be undone such as unban
DELIMITER $$
DROP PROCEDURE IF EXISTS `drop_index_moderation_logs` $$
CREATE PROCEDURE `drop_index_moderation_logs`()
BEGIN

IF EXISTS (
  SELECT 1
  FROM INFORMATION_SCHEMA.STATISTICS
  WHERE TABLE_NAME = 'moderation_logs'
    AND INDEX_NAME = 'IDX_MODERATION_LOGS_INSTANCE_ID_ACTION_TYPE_ENTITY_ID'
)
THEN
  DROP INDEX `IDX_MODERATION_LOGS_INSTANCE_ID_ACTION_TYPE_ENTITY_ID` ON `moderation_logs`;
END IF;

END $$

DELIMITER ;

CALL `drop_index_moderation_logs`();
DROP PROCEDURE IF EXISTS `drop_index_moderation_logs`;
