/**
  Private Message
 */
CREATE TABLE `private_message`
(
    `id`               BIGINT AUTO_INCREMENT PRIMARY KEY,
    `sender_id`        BIGINT                                    NOT NULL,
    `recipient_id`     BIGINT                                    NOT NULL,
    `content`          TEXT                                      NOT NULL,
    `deleted`          BOOLEAN DEFAULT FALSE                     NOT NULL,
    `read`             BOOLEAN DEFAULT FALSE                     NOT NULL,
    `created_at`       TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
    `updated_at`      TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP(3)  NOT NULL ON UPDATE CURRENT_TIMESTAMP(3)
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

