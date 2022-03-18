

-- sbootgodcoder
-- 1111

CREATE TABLE board (
     id BIGINT auto_increment NULL,
     title varchar(50) NULL,
     content TEXT NULL,
     CONSTRAINT board_pk PRIMARY KEY (id)
)
    ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci
COMMENT='게시판';


CREATE TABLE `user` (
        `id` bigint(20) NOT NULL AUTO_INCREMENT,
        `username` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
        `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
        `enabled` bit(1) NOT NULL,
        PRIMARY KEY (`id`),
        UNIQUE KEY `user_un` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `role` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `user_role` (
                             `user_id` bigint(20) NOT NULL,
                             `role_id` bigint(20) NOT NULL,
                             PRIMARY KEY (`user_id`,`role_id`),
                             KEY `user_role_FK_1` (`role_id`),
                             CONSTRAINT `user_role_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                             CONSTRAINT `user_role_FK_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;