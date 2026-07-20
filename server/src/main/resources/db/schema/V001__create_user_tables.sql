CREATE TABLE `user` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'User primary key',
    `username` VARCHAR(50) NOT NULL COMMENT 'Unique login name',
    `email` VARCHAR(254) NOT NULL COMMENT 'Unique email address',
    `password_hash` VARCHAR(100) NOT NULL COMMENT 'BCrypt password hash',
    `nickname` VARCHAR(50) NOT NULL COMMENT 'Public display name',
    `avatar_url` VARCHAR(500) NULL COMMENT 'Avatar URL',
    `bio` VARCHAR(255) NULL COMMENT 'Short user biography',
    `status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '0-disabled, 1-enabled',
    `created_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'Creation time',
    `updated_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3)
        ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'Last update time',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_username` (`username`),
    UNIQUE KEY `uk_user_email` (`email`),
    KEY `idx_user_created_at` (`created_at`),
    CONSTRAINT `chk_user_status` CHECK (`status` IN (0, 1))
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  COMMENT = 'Community users';

CREATE TABLE `role` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Role primary key',
    `code` VARCHAR(50) NOT NULL COMMENT 'Unique role code, for example USER or ADMIN',
    `name` VARCHAR(50) NOT NULL COMMENT 'Role display name',
    `description` VARCHAR(255) NULL COMMENT 'Role description',
    `status` TINYINT UNSIGNED NOT NULL DEFAULT 1 COMMENT '0-disabled, 1-enabled',
    `created_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'Creation time',
    `updated_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3)
        ON UPDATE CURRENT_TIMESTAMP(3) COMMENT 'Last update time',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_code` (`code`),
    CONSTRAINT `chk_role_status` CHECK (`status` IN (0, 1))
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  COMMENT = 'System roles';

CREATE TABLE `user_role` (
    `user_id` BIGINT UNSIGNED NOT NULL COMMENT 'User ID',
    `role_id` BIGINT UNSIGNED NOT NULL COMMENT 'Role ID',
    `created_at` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT 'Assignment time',
    PRIMARY KEY (`user_id`, `role_id`),
    KEY `idx_user_role_role_id` (`role_id`),
    CONSTRAINT `fk_user_role_user`
        FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_user_role_role`
        FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  COMMENT = 'User-role assignments';
