/**
  Private Message
 */
CREATE TABLE `private_messages`
(
    `id`               BIGINT AUTO_INCREMENT PRIMARY KEY,
    `sender_id`        BIGINT                                    NOT NULL,
    `recipient_id`     BIGINT                                    NOT NULL,
    `activity_pub_id`  TEXT                                      NOT NULL,
    `content`          TEXT                                      NOT NULL,
    `is_local`         BOOLEAN DEFAULT FALSE                     NOT NULL,
    `is_deleted`       BOOLEAN DEFAULT FALSE                     NOT NULL,
    `is_read`          BOOLEAN DEFAULT FALSE                     NOT NULL,
    `created_at`       TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
    `updated_at`       TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_PRIVATE_MESSAGES_SENDER_ID` ON `private_messages` (`sender_id`);
CREATE INDEX `IDX_PRIVATE_MESSAGES_RECIPIENT_ID` ON `private_messages` (`recipient_id`);
