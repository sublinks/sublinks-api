/**
  Custom Emoji table
 */
CREATE TABLE `custom_emojis` (
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
CREATE TABLE `custom_emoji_keywords` (
    `id`              BIGINT AUTO_INCREMENT PRIMARY KEY,
    `custom_emoji_id` BIGINT       NOT NULL,
    `keyword`         VARCHAR(255) NOT NULL,
    FOREIGN KEY (`custom_emoji_id`) REFERENCES `custom_emojis` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET `utf8mb4`
  COLLATE = 'utf8mb4_unicode_ci';

CREATE INDEX `IDX_CUSTOM_EMOJI_KEYWORD_CUSTOM_EMOJI_ID` ON `custom_emoji_keywords` (`custom_emoji_id`);


